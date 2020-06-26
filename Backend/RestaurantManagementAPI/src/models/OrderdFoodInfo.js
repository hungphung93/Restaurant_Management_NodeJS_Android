export class OrderedFoodInfo {
    constructor(transactionId, tableName, orderId, foodId, foodName, imageURL, foodStatus, quantity, price, updated_at) {
        this.transactionId = transactionId;
        this.tableName = tableName;
        this.orderId = orderId;
        this.foodId = foodId;
        this.foodName = foodName;
        this.imageURL = imageURL;
        this.foodStatus = foodStatus;
        this.quantity = quantity;
        this.price = price;
        this.updated_at = updated_at;
    }
}