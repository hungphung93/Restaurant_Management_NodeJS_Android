export class Food {
    constructor(foodId, foodName, foodDescription, imageURL, category, price, isDeleted, deletedAt) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.foodDescription = foodDescription;
        this.imageURL = imageURL;
        this.category = category;
        this.price = price;
        this.isDeleted = isDeleted;
        this.deletedAt = deletedAt;
    }
}