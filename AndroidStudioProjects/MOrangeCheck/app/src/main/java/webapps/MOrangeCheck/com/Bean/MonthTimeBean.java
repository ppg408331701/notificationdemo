package webapps.MOrangeCheck.com.Bean;



public class MonthTimeBean  {
    String name;
    boolean type;
    private String tag;//所属的分类（城市的汉语拼音首字母）

    public String getName() {  
        return name;  
    }  
  
    public void setName(String name) {  
        this.name = name;  
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}