// Emits the specified event for each consumer. Consumer will be notified if they are subscribed to the event
// event: event to emitted
// asset_status: asset status that triggered event to be fired
// consumers: array of consumers
module.exports = function notifyConsumers(event, asset_status, consumers) {

    if (event !== null){

        consumers.forEach(consumer => {

            consumer.emit(event, asset_status);
        })          
    }
}