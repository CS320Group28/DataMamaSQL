package EntityClasses;

public class Category implements EntityType<Category>{
    private String categoryName;
    private int categoryID;

    public Category(String categoryName){
        this.categoryName=categoryName;
    }

    public Category(String categoryName, int categoryID){
        this.categoryName = categoryName;
        this.categoryID = categoryID;
    }

    public void setCategoryName(String name){
        this.categoryName = name;
    }

    public String getCategoryName(){
        return this.categoryName;
    }

    public void setCategoryID(int id){
        this.categoryID = id;
    }

    public int getCategoryID(){
        return this.categoryID;
    }

    @Override
    public boolean InsertEntity(Category entity) {


        return false;
    }

    @Override
    public boolean DeleteEntity(Category entity) {
        return false;
    }

    @Override
    public boolean UpdateEntity(Category oldEntity, Category newEntity) {
        return false;
    }



}
