package priv.linjb.learn.company.labscare;
/**
 * @author: 林嘉宝
 *  
 * @Date: 2019/5/22
 *  
 * @name: 
 *
 * @Description: 
 */
public class DicEntity {

    private String dicType;

    private String dicName;

    private String dicParentCode;

    private String dicCode;

    private String dicValue;

    private String leaf;

    private String desc;

    private Integer sort;

    public DicEntity(String dicName, String dicParentCode, String dicCode) {
        this.dicName = dicName;
        this.dicParentCode = dicParentCode;
        this.dicCode = dicCode;
    }

    public String getLeaf() {
        return leaf;
    }

    public void setLeaf(String leaf) {
        this.leaf = leaf;
    }

    public String getDicType() {
        return dicType;
    }

    public void setDicType(String dicType) {
        this.dicType = dicType;
    }

    public String getDicCode() {
        return dicCode;
    }

    public void setDicCode(String dicCode) {
        this.dicCode = dicCode;
    }

    public String getDicParentCode() {
        return dicParentCode;
    }

    public void setDicParentCode(String dicParentCode) {
        this.dicParentCode = dicParentCode;
    }

    public String getDicName() {
        return dicName;
    }

    public void setDicName(String dicName) {
        this.dicName = dicName;
    }

    public String getDicValue() {
        return dicValue;
    }

    public void setDicValue(String dicValue) {
        this.dicValue = dicValue;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
