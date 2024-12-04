package com.tfx.interfaces;

import com.tfx.model.Master;

/**
 * @author tianfx
 * @date 2024/12/4 17:42
 */
public interface DieNoticeHandle {
    
    default void notice(Master master) {}
}
