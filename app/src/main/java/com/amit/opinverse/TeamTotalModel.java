package com.amit.opinverse;

public class TeamTotalModel {
    String sl_no, level_no, name, sponsor_id,  pos, joining_on, package_id, upgd_on, status;
    TeamTotalModel(String sl_no, String level_no, String name, String sponsor_id, String pos, String joining_on, String package_id, String upgd_on, String status){
        this.sl_no = sl_no;
        this.level_no = level_no;
        this.name = name;
        this.sponsor_id = sponsor_id;
        this.pos = pos;
        this.joining_on = joining_on;
        this.package_id = package_id;
        this.upgd_on = upgd_on;
        this.status = status;
    }

    public String getJoining_on() {
        return joining_on;
    }

    public String getLevel_no() {
        return level_no;
    }

    public String getName() {
        return name;
    }

    public String getPackage_id() {
        return package_id;
    }

    public String getPos() {
        return pos;
    }

    public String getSl_no() {
        return sl_no;
    }

    public String getSponsor_id() {
        return sponsor_id;
    }

    public String getStatus() {
        return status;
    }

    public String getUpgd_on() {
        return upgd_on;
    }
}
