package com.member.mapper;

import com.member.entities.po.ProjectPO;
import com.member.entities.po.ProjectPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProjectPOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project
     *
     * @mbg.generated Wed Apr 13 21:45:49 CST 2022
     */
    long countByExample(ProjectPOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project
     *
     * @mbg.generated Wed Apr 13 21:45:49 CST 2022
     */
    int deleteByExample(ProjectPOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project
     *
     * @mbg.generated Wed Apr 13 21:45:49 CST 2022
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project
     *
     * @mbg.generated Wed Apr 13 21:45:49 CST 2022
     */
    int insert(ProjectPO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project
     *
     * @mbg.generated Wed Apr 13 21:45:49 CST 2022
     */
    int insertSelective(ProjectPO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project
     *
     * @mbg.generated Wed Apr 13 21:45:49 CST 2022
     */
    List<ProjectPO> selectByExample(ProjectPOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project
     *
     * @mbg.generated Wed Apr 13 21:45:49 CST 2022
     */
    ProjectPO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project
     *
     * @mbg.generated Wed Apr 13 21:45:49 CST 2022
     */
    int updateByExampleSelective(@Param("record") ProjectPO record, @Param("example") ProjectPOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project
     *
     * @mbg.generated Wed Apr 13 21:45:49 CST 2022
     */
    int updateByExample(@Param("record") ProjectPO record, @Param("example") ProjectPOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project
     *
     * @mbg.generated Wed Apr 13 21:45:49 CST 2022
     */
    int updateByPrimaryKeySelective(ProjectPO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project
     *
     * @mbg.generated Wed Apr 13 21:45:49 CST 2022
     */
    int updateByPrimaryKey(ProjectPO record);
}