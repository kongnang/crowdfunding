package com.member.service;

import com.member.entities.vo.ProjectVO;

/**
 * @author qiu
 * @create 2022-04-14 16:34
 */
public interface ProjectService {

    void addProject(ProjectVO projectVO, Integer memberId) throws Exception;
}
