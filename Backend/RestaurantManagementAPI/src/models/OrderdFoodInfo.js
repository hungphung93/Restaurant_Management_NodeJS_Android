export class OrderedFoodInfo {
    constructor(transactionId, tableName, foodId, foodName, imageURL, foodStatus, quantity, price) {
        this.transactionId = transactionId;
        this.tableName = tableName;
        this.foodId = foodId;
        this.foodName = foodName;
        this.imageURL = imageURL;
        this.foodStatus = foodStatus;
        this.quantity = quantity;
        this.price = price;
    }
}