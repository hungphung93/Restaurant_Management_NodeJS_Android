export class Food {
    constructor(foodId, foodName, foodDescsription, imageURL, category, price, isDeleted, deletedAt) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.foodDescsription = foodDescsription;
        this.imageURL = imageURL;
        this.category = category;
        this.price = price;
        this.isDeleted = isDeleted;
        this.deletedAt = deletedAt;
    }
}