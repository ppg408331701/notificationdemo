package webapps.MOrangeCheck.com.Bean;

import me.yokeyword.indexablerv.IndexableEntity;

/**
 * Created by ppg777 on 2017/3/14.
 */

public class CompanyMeber implements IndexableEntity{

    private String name;
    private String pinyin;
    private String avatar;
    private String gender;
    private String pistion;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPistion() {
        return pistion;
    }

    public void setPistion(String pistion) {
        this.pistion = pistion;
    }

    @Override
    public String getFieldIndexBy() {
        return name;
    }

    @Override
    public void setFieldIndexBy(String indexField) {
        this.name = indexField;
    }

    @Override
    public void setFieldPinyinIndexBy(String pinyin) {

    }
}
