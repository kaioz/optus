package com.cocosw.optus.model;

import java.util.HashMap;

public class Route implements java.io.Serializable {
    private static final long serialVersionUID = 2599929198439885616L;
    public int id;
    public RouteLocation location;
    public String name;
    public HashMap<String,String> fromcentral;
}
