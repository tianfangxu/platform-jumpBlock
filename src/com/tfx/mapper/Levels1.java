package com.tfx.mapper;

import com.tfx.Subject;
import com.tfx.interfaces.MapperManage;
import com.tfx.model.Master;
import com.tfx.model.Region;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tianfx
 * @date 2024/12/5 10:43
 */
public class Levels1 implements MapperManage {

    List<Region> areas;
    
    public static Levels1 getInstance(){
        Levels1 levels1 = new Levels1();
        levels1.areas = new ArrayList<>();
        return levels1;
    }
    
    @Override
    public List<Region> intiArea() {
        areas.add(Region.getStartRegion(300));
        areas.add(getRegion1());
        areas.add(getRegion2());
        return areas;
    }
    
    public Region getRegion1(){
        Region instance = Region.getInstance();
        instance.setLocation(360,Subject.START_LOCAL[1]+Master.HEIGHT);
        instance.setSize(300);
        return instance;
    }

    public Region getRegion2(){
        Region instance = Region.getInstance();
        instance.setLocation(780,Subject.START_LOCAL[1]+Master.HEIGHT);
        instance.setSize(300);
        return instance;
    }
}
