package com.amit.opinverse;

public class ModuleModel {
    private String moduleName, moduleDuration, img_url, video_url;
    String description;
    private int moduleProgress, moduleTasksDone, moduleTotalTasks, moduleRating;

    ModuleModel(){

    }

    ModuleModel(String moduleName,String description,  String moduleDuration, int moduleProgress, int moduleTasksDone, int moduleTotalTasks, int moduleRating, String video_url, String img_url){
        this.moduleName = moduleName;
        this.moduleDuration = moduleDuration;
        this.moduleProgress = moduleProgress;
        this.moduleTasksDone = moduleTasksDone;
        this.moduleTotalTasks = moduleTotalTasks;
        this.moduleRating = moduleRating;
        this.description = description;
        this.video_url = video_url;
        this.img_url = img_url;
    }

    public int getModuleProgress() {
        return moduleProgress;
    }

    public int getModuleTasksDone() {
        return moduleTasksDone;
    }

    public int getModuleTotalTasks() {
        return moduleTotalTasks;
    }

    public String getModuleDuration() {
        return moduleDuration;
    }

    public String getModuleName() {
        return moduleName;
    }

    public int getModuleRating() {
        return moduleRating;
    }

    public String getVideo_url() {
        return video_url;
    }

    public String getDescription() {
        return description;
    }

    public String getImg_url() {
        return img_url;
    }
}
