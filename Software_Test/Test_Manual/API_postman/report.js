const newman = require('newman');
newman.run({
collection: require('customer_api_collection.json'), 
environment: require('customer_api_env.json'),
iterationCount: 1,
reporters: 'htmlextra',
reporter: {
htmlextra: {
export: './Reports/report.html',
}
}
}, function (err) {
if (err) { throw err; }
console.log('collection run complete!');
});