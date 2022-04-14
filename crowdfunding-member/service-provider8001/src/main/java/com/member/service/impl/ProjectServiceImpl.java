package com.member.service.impl;

import com.member.entities.po.ProjectPO;
import com.member.entities.po.RewardPO;
import com.member.entities.po.SponsorConfirmInfoPO;
import com.member.entities.po.SponsorInfoPO;
import com.member.entities.vo.ProjectVO;
import com.member.entities.vo.RewardVO;
import com.member.entities.vo.SponsorConfirmInfoVO;
import com.member.entities.vo.SponsorInfoVO;
import com.member.mapper.*;
import com.member.service.ProjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author qiu
 * @create 2022-04-14 16:34
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectItemPictureMapper projectItemPictureMapper;

    @Autowired
    ProjectMapper projectMapper;

    @Autowired
    RewardMapper rewardMapper;

    @Autowired
    SponsorConfirmInfoMapper sponsorConfirmInfoMapper;

    @Autowired
    SponsorInfoMapper sponsorInfoMapper;

    /**
     * 保存所有项目信息
     * @param projectVO
     * @param memberId
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public void addProject(ProjectVO projectVO, Integer memberId) throws Exception{

        System.out.println(projectVO);

        ProjectPO projectPO = new ProjectPO();

        // 保存项目基本信息
        BeanUtils.copyProperties(projectVO,projectPO);
        projectPO.setMemberId(memberId);
        projectPO.setCreateTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        projectPO.setStatus(0);
        projectMapper.insertSelective(projectPO);
        Integer projectId = projectPO.getId();

        // 关联分类信息
        List<Integer> typeIdList = projectVO.getTypeIdList();
        if(!Objects.isNull(typeIdList) && typeIdList.size() != 0){
            projectMapper.insertRelationshipWithType(typeIdList,projectId);
        }

        // 关联标签信息
        List<Integer> tagIdList = projectVO.getTagIdList();
        if(!Objects.isNull(tagIdList) && tagIdList.size() != 0){
            projectMapper.insertRelationshipWithTag(tagIdList,projectId);
        }

        // 保存项目详情图片
        List<String> picturePath = projectVO.getItemPicturePath();
        projectItemPictureMapper.insertRelationshipWithPicture(picturePath,projectId);

        // 保存发起人信息
        SponsorInfoPO sponsorInfoPO = new SponsorInfoPO();
        SponsorInfoVO sponsorInfoVO = projectVO.getSponsorInfoVO();
        BeanUtils.copyProperties(sponsorInfoVO,sponsorInfoPO);
        sponsorInfoPO.setMemberId(memberId);
        sponsorInfoMapper.insertSelective(sponsorInfoPO);

        // 保存回报信息
        List<RewardVO> rewardVOList = projectVO.getRewardVOList();
        if(!Objects.isNull(rewardVOList) && rewardVOList.size() != 0){
            ArrayList<RewardPO> rewardPOList = new ArrayList<>();
            for (RewardVO rewardVO : rewardVOList) {
                RewardPO rewardPO = new RewardPO();
                BeanUtils.copyProperties(rewardVO,rewardPO);
                rewardPOList.add(rewardPO);
            }
            rewardMapper.insertBatch(rewardPOList,projectId);
        }

        // 保存确认信息
        SponsorConfirmInfoPO sponsorConfirmInfoPO = new SponsorConfirmInfoPO();
        SponsorConfirmInfoVO sponsorConfirmInfoVO = projectVO.getSponsorConfirmInfoVO();
        BeanUtils.copyProperties(sponsorConfirmInfoVO,sponsorConfirmInfoPO);
        sponsorConfirmInfoPO.setMemberId(memberId);
        sponsorConfirmInfoMapper.insertSelective(sponsorConfirmInfoPO);
    }
}
