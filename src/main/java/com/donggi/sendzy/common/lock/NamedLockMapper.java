package com.donggi.sendzy.common.lock;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface NamedLockMapper {

    int getLock(@Param("lockName") final String lockName, @Param("timeout") final int timeout);

    int releaseLock(@Param("lockName") final String lockName);
}
