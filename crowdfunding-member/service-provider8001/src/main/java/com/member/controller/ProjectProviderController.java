package com.member.controller;

import com.member.entities.vo.ProjectVO;
import com.member.service.ProjectService;
import com.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qiu
 * @create 2022-04-14 16:27
 */
@RestController
public class ProjectProviderController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/provider/project/add")
    public ResultEntity<String> addProject(@RequestBody ProjectVO projectVO, @RequestParam("memberId") Integer memberId){
        try {
            projectService.addProject(projectVO,memberId);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.fail(e.getMessage());
        }
    }

}
