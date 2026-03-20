package com.lyqf.qianfanmall.db.service;

import com.lyqf.qianfanmall.db.dao.QianfanmallPermissionMapper;
import com.lyqf.qianfanmall.db.dao.QianfanmallRoleMapper;
import com.lyqf.qianfanmall.db.domain.QianfanmallPermission;
import com.lyqf.qianfanmall.db.domain.QianfanmallPermissionExample;
import com.lyqf.qianfanmall.db.domain.QianfanmallRole;
import com.lyqf.qianfanmall.db.domain.QianfanmallRoleExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class QianfanmallPermissionService {
    @Resource
    private QianfanmallPermissionMapper permissionMapper;

    public Set<String> queryByRoleIds(Integer[] roleIds) {
        Set<String> permissions = new HashSet<String>();
        if(roleIds.length == 0){
            return permissions;
        }

        QianfanmallPermissionExample example = new QianfanmallPermissionExample();
        example.or().andRoleIdIn(Arrays.asList(roleIds)).andDeletedEqualTo(false);
        List<QianfanmallPermission> permissionList = permissionMapper.selectByExample(example);

        for(QianfanmallPermission permission : permissionList){
            permissions.add(permission.getPermission());
        }

        return permissions;
    }


    public Set<String> queryByRoleId(Integer roleId) {
        Set<String> permissions = new HashSet<String>();
        if(roleId == null){
            return permissions;
        }

        QianfanmallPermissionExample example = new QianfanmallPermissionExample();
        example.or().andRoleIdEqualTo(roleId).andDeletedEqualTo(false);
        List<QianfanmallPermission> permissionList = permissionMapper.selectByExample(example);

        for(QianfanmallPermission permission : permissionList){
            permissions.add(permission.getPermission());
        }

        return permissions;
    }

    public Set<String> queryByRoleId(List<Integer> roleIds) {
        Set<String> permissions = new HashSet<String>();
        if(roleIds == null || roleIds.isEmpty()){
            return permissions;
        }

        QianfanmallPermissionExample example = new QianfanmallPermissionExample();
        example.or().andRoleIdIn(roleIds).andDeletedEqualTo(false);
        List<QianfanmallPermission> permissionList = permissionMapper.selectByExample(example);

        for(QianfanmallPermission permission : permissionList){
            permissions.add(permission.getPermission());
        }

        return permissions;
    }

    public boolean checkSuperPermission(Integer roleId) {
        if(roleId == null){
            return false;
        }

        QianfanmallPermissionExample example = new QianfanmallPermissionExample();
        example.or().andRoleIdEqualTo(roleId).andPermissionEqualTo("*").andDeletedEqualTo(false);
        return permissionMapper.countByExample(example) != 0;
    }

    public boolean checkSuperPermission(List<Integer> roleIds) {
        if(roleIds == null || roleIds.isEmpty()){
            return false;
        }

        QianfanmallPermissionExample example = new QianfanmallPermissionExample();
        example.or().andRoleIdIn(roleIds).andPermissionEqualTo("*").andDeletedEqualTo(false);
        return permissionMapper.countByExample(example) != 0;
    }

    public void deleteByRoleId(Integer roleId) {
        QianfanmallPermissionExample example = new QianfanmallPermissionExample();
        example.or().andRoleIdEqualTo(roleId).andDeletedEqualTo(false);
        permissionMapper.logicalDeleteByExample(example);
    }

    public void add(QianfanmallPermission qianfanmallPermission) {
        qianfanmallPermission.setAddTime(LocalDateTime.now());
        qianfanmallPermission.setUpdateTime(LocalDateTime.now());
        permissionMapper.insertSelective(qianfanmallPermission);
    }
}
