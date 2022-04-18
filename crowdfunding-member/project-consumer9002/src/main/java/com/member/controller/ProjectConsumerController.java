package com.member.controller;

import com.constant.CrowFundingConstant;
import com.member.config.OSSProperties;
import com.member.entities.po.Member;
import com.member.entities.vo.MemberLoginVO;
import com.member.entities.vo.ProjectVO;
import com.member.entities.vo.RewardVO;
import com.member.entities.vo.SponsorConfirmInfoVO;
import com.member.service.ProjectFeignService;
import com.util.CrowFundingUtil;
import com.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @author qiu
 * @create 2022-04-14 9:45
 */
@Controller
public class ProjectConsumerController {

    @Autowired
    private OSSProperties ossProperties;

    @Autowired
    private ProjectFeignService projectFeignService;


    /**
     * 保存确认信息后，保存整个项目的所有信息
     * @param confirmInfoVO
     * @param session
     * @param modelMap
     * @return
     */
    @PostMapping("/do/confirm")
    public String saveConfirmInfo(SponsorConfirmInfoVO confirmInfoVO, HttpSession session, ModelMap modelMap){

        ProjectVO projectVO = (ProjectVO)session.getAttribute(CrowFundingConstant.PROJECT_INFO);
        if(Objects.isNull(projectVO)){
            throw new RuntimeException(CrowFundingConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
        }
        // 保存确认信息
        projectVO.setSponsorConfirmInfoVO(confirmInfoVO);

        // 保存整个项目信息，与用户关联
        MemberLoginVO member =(MemberLoginVO) session.getAttribute(CrowFundingConstant.MEMBER_LOGIN_NAME);
        Integer memberId = member.getId();
        ResultEntity<String> resultEntity = projectFeignService.addProject(projectVO, memberId);

        if (ResultEntity.FAILE.equals(resultEntity.getOperationResult())){
            modelMap.addAttribute("message",resultEntity.getOperationMessage());
            return "project-confirm";
        }

        // 添加成功，删除session中的projectVO
        session.removeAttribute(CrowFundingConstant.PROJECT_INFO);

        return "redirect:http://localhost/member/project/finish";
    }

    /**
     * 接收回报信息，保存到projectVO中
     * @param rewardVO
     * @param session
     * @return
     */
    @PostMapping("/do/reward.json")
    public ResultEntity<String> saveRewardInfo(RewardVO rewardVO, HttpSession session){

        try {
            ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowFundingConstant.PROJECT_INFO);
            if(Objects.isNull(projectVO)){
                return ResultEntity.fail(CrowFundingConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
            }

            // 获取回报信息对象
            List<RewardVO> rewardVOList = projectVO.getRewardVOList();
            if(Objects.isNull(rewardVOList) || rewardVOList.size() == 0){
                rewardVOList = new ArrayList<RewardVO>();
                projectVO.setRewardVOList(rewardVOList);
            }
            rewardVOList.add(rewardVO);

            // 存入session
            session.setAttribute(CrowFundingConstant.PROJECT_INFO,projectVO);

            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.fail(e.getMessage());
        }
    }

    /**
     * 上传回报设置信息中的图片
     * @param rewardPicture
     * @return 图片保存路径
     */
    @PostMapping("/do/reward/upload.json")
    public ResultEntity<String> uploadPicture(@RequestParam("rewardPicture") MultipartFile rewardPicture) throws IOException {

        ResultEntity<String> uploadResultEntity = CrowFundingUtil.uploadFileToOSS(ossProperties.getEndPoint(),
                ossProperties.getBucketName(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(),
                ossProperties.getBucketDomain(),
                rewardPicture.getInputStream(),
                rewardPicture.getOriginalFilename());

        return uploadResultEntity;
    }

    /**
     * @param projectVO 接收除了上传图片之外的其他普通数据
     * @param headerPicture 接收上传的头图
     * @param itemPicture 接收上传的详情图片
     * @param session 用来将收集了一部分数据的ProjectVO 对象存入Session 域
     * @param modelMap 用来在当前操作失败后返回上一个表单页面时携带提示消息
     * @return
     */
    @PostMapping("/save/info")
    public String saveProjectInfo(ProjectVO projectVO,
                                  MultipartFile headerPicture,
                                  List<MultipartFile> itemPicture,
                                  HttpSession session,
                                  ModelMap modelMap) throws IOException {

        String endPoint = ossProperties.getEndPoint();
        String bucketName = ossProperties.getBucketName();
        String accessKeyId = ossProperties.getAccessKeyId();
        String accessKeySecret = ossProperties.getAccessKeySecret();
        String bucketDomain = ossProperties.getBucketDomain();

        // 1. 上传头图
        if (headerPicture.isEmpty()) {
            modelMap.addAttribute("message", CrowFundingConstant.MESSAGE_HEADER_PICTURE);
            return "project-info";
        }

        ResultEntity<String> headResultEntity = CrowFundingUtil.uploadFileToOSS(endPoint, bucketName, accessKeyId, accessKeySecret, bucketDomain, headerPicture.getInputStream(), headerPicture.getOriginalFilename());

        // 上传成功
        if (ResultEntity.SUCCESS.equals(headResultEntity.getOperationResult())) {
            String uploadPath = headResultEntity.getData();
            projectVO.setHeaderPicturePath(uploadPath);
        } else { // 上传失败
            modelMap.addAttribute("message", CrowFundingConstant.MESSAGE_UPLOAD_FAILED);
            return "project-info";
        }

        // 2.上传项目详情图片
        List<String> uploadPaths = new ArrayList<>();

        if (Objects.isNull(itemPicture) || itemPicture.size() == 0) {
            modelMap.addAttribute("message", CrowFundingConstant.MESSAGE_ITEM_PICTURE);
            return "project-info";
        }
        for (MultipartFile file : itemPicture) {

            ResultEntity<String> resultEntity = CrowFundingUtil.uploadFileToOSS(endPoint, bucketName, accessKeyId, accessKeySecret, bucketDomain,  file.getInputStream(),file.getOriginalFilename());

            // 上传成功
            if (ResultEntity.SUCCESS.equals(resultEntity.getOperationResult())) {
                String uploadPath = resultEntity.getData();
                uploadPaths.add(uploadPath);
            } else { // 上传失败
                modelMap.addAttribute("message", CrowFundingConstant.MESSAGE_UPLOAD_FAILED);
                return "project-info";
            }
        }
        projectVO.setItemPicturePath(uploadPaths);

        // 3.存入session域
        session.setAttribute(CrowFundingConstant.PROJECT_INFO, projectVO);

        return "redirect:http://localhost/member/project/reward";
    }

    /* 完成页面*/
    @GetMapping("/finish")
    public String finishPage(){
        return "project-finish";
    }


    /* 确认信息页面*/
    @GetMapping("/confirm")
    public String confirmPage(){
        return "project-confirm";
    }

    /* 回报设置页面*/
    @GetMapping("/reward")
    public String rewardPage(){
        return "project-reward";
    }

    /* 项目及发起人信息*/
    @GetMapping("/info")
    public String projectInfoPage(){
        return "project-info";
    }

    /* 众筹平台项目发起人协议*/
    @GetMapping("/protocol")
    public String protocolPage(){
        return "project-protocol";
    }

}
