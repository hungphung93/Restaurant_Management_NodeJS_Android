export class Transaction {
    constructor(transactionId, tableName, status, openAt, orderedFoods, totalAmount) {
        this.transactionId = transactionId;
        this.tableName = tableName;
        this.status = status;
        this.openAt = openAt;
        this.orderedFoods = orderedFoods;
        this.totalAmount = totalAmount;
    }
}
