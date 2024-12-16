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
        areas.add(Region.getStartRegion(200,"reg-start"));
        areas.add(getRegion1());
        areas.add(getRegion2());
        areas.add(getRegion3());
        areas.add(getRegion4());
        initLinkedList();
        return areas;
    }
    
    public void initLinkedList(){
        for (int i = 0; i < areas.size(); i++) {
            if (i-1 > -1){
                areas.get(i).setPre(areas.get(i-1));
            }
            if (i+1 < areas.size()){
                areas.get(i).setNext(areas.get(i+1));
            }
        }
    }
    
    public Region getRegion1(){
        Region instance = Region.getInstance("reg-1");
        instance.setLocation(201,Subject.START_LOCAL[1]+Master.HEIGHT+60);
        instance.setSize(199);
        return instance;
    }

    public Region getRegion2(){
        Region instance = Region.getInstance("reg-2");
        instance.setLocation(401,Subject.START_LOCAL[1]+Master.HEIGHT);
        instance.setSize(199);
        return instance;
    }

    public Region getRegion3(){
        Region instance = Region.getInstance("reg-3");
        instance.setLocation(601,(Subject.START_LOCAL[1]+Master.HEIGHT+500));
        instance.setSize(199);
        return instance;
    }

    public Region getRegion4(){
        Region instance = Region.getInstance("reg-3");
        instance.setLocation(801,(Subject.START_LOCAL[1]+Master.HEIGHT+30));
        instance.setSize(199);
        return instance;
    }
}
