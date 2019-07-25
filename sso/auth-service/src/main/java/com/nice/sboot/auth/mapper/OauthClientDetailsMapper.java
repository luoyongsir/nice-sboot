package com.nice.sboot.auth.mapper;

import com.nice.sboot.auth.entity.OauthClientDetails;

/**
 * This interface corresponds to the database table oauth_client_details
 * @author luoyong
 * @date 2019/07/25 18:38
 */
public interface OauthClientDetailsMapper {
    /**
     * 根据主键删除数据库记录
     * @author luoyong
     * @date 2019/07/25 18:38
     * @param clientId
     * @return 
     */
    int deleteByPrimaryKey(String clientId);

    /**
     * 新增记录
     * @author luoyong
     * @date 2019/07/25 18:38
     * @param record
     * @return 
     */
    int insert(OauthClientDetails record);

    /**
     * 新增记录
     * @author luoyong
     * @date 2019/07/25 18:38
     * @param record
     * @return 
     */
    int insertSelective(OauthClientDetails record);

    /**
     * 根据主键查询记录
     * @author luoyong
     * @date 2019/07/25 18:38
     * @param clientId
     * @return 
     */
    OauthClientDetails selectByPrimaryKey(String clientId);

    /**
     * 根据主键修改数据库记录
     * @author luoyong
     * @date 2019/07/25 18:38
     * @param record
     * @return 
     */
    int updateByPrimaryKeySelective(OauthClientDetails record);

    /**
     * 根据主键修改数据库记录
     * @author luoyong
     * @date 2019/07/25 18:38
     * @param record
     * @return 
     */
    int updateByPrimaryKey(OauthClientDetails record);
}