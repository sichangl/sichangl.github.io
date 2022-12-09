const express = require("express");
const app = express();
var cors = require('cors')
const bodyParser = require('body-parser');
const axios = require('axios');
const { response } = require("express");
var MY_API_TOKEN = '5WB5xymf0iQwlxcHxNpE1aZT3sqvyKNELqwbePGxfuAHFGJ-0FsQYcXZ_gmbMvT38VN3b5TGMwm-LzqKrL8IcyGP-dHpdf-hOMgcsPhthKBZGmkX3G35fSHKULg3Y3Yx'

var api_key = 'encOYtZ0u7RnFmoUOA69xlXNE';
var api_secret_key = 'YgLALXL2jTLwTrzHZQQSMBZM513E02mLCfLYFVlBcB5W0EnVIc';
var token = 'AAAAAAAAAAAAAAAAAAAAAAGLiwEAAAAA5g9s4bXcjN989xR%2FX5UZ0lmQbVU%3DjboCxVzOqsVMmcVoyN4o1iBm7gH7oDf8zWkxvOFJCs7OGippIF'


app.use(cors())

app.set('trust proxy', true);

app.get("/", (req, res) => {
    res.send("app is working")
});

app.get("/search", (req, res) => {
    console.log(req.query)
    var term = req.query.term;
    var categories = req.query.categories;
    var distance = req.query.distance;
    var latitude = req.query.latitude;
    var longitude = req.query.longitude;
    var distanceInMeter = distance * 1609.34;
    var yelpUrl = "https://api.yelp.com/v3/businesses/search?";
    yelpUrl = yelpUrl + "term=" + term + "&categories=" + categories + "&distance=" + distanceInMeter + "&latitude=" + latitude + "&longitude=" + longitude;
    console.log(yelpUrl)
    axios.get(yelpUrl, {
        headers: {
            'Authorization' : 'Bearer 5WB5xymf0iQwlxcHxNpE1aZT3sqvyKNELqwbePGxfuAHFGJ-0FsQYcXZ_gmbMvT38VN3b5TGMwm-LzqKrL8IcyGP-dHpdf-hOMgcsPhthKBZGmkX3G35fSHKULg3Y3Yx'
        }
    }).then(response => {                       // this is an axios response object (https://github.com/axios/axios#response-schema)
            console.log(response.data);           // this is the response body from tiltify (https://tiltify.github.io/api/endpoints/campaigns-id-donations.html)
            res.send(response.data);
            res.end();
    }).catch(error => {
            console.log(error);
    });
})

// app.get("/autoSearch", (res, req) => {
//     console.log("receive message from fronted end")
//     var term = req.query.term;
//     var categories = req.query.categories;
//     var latitude = req.query.latitude;
//     var longitude = req.query.longitude;
//     var distance = req.query.distance;
//     var distanceInMeter = distance * 1609.34;
//     var yelpUrl = "https://api.yelp.com/v3/businesses/search?";
//     yelpUrl = yelpUrl + "term=" + term + "&categories=" + categories + "&distance=" + distanceInMeter + "&latitude=" + latitude + "&longitude" + longitude;
//     console.log(yelpUrl)
//     axios.get(yelpUrl, {
//         headers: {
//             'Authorization' : 'Bearer 5WB5xymf0iQwlxcHxNpE1aZT3sqvyKNELqwbePGxfuAHFGJ-0FsQYcXZ_gmbMvT38VN3b5TGMwm-LzqKrL8IcyGP-dHpdf-hOMgcsPhthKBZGmkX3G35fSHKULg3Y3Yx'
//         }
//     }).then(response => {                       // this is an axios response object (https://github.com/axios/axios#response-schema)
//             console.log(response.data);           // this is the response body from tiltify (https://tiltify.github.io/api/endpoints/campaigns-id-donations.html)
//             res.send(response.data.businesses);
//             res.end();
//     }).catch(error => {
//             console.log(error);
//     }); 
// })

app.get("/autoComplete", (req, res) => {
    console.log(req)
    var text = req.query.text;
    console.log(text);
    var url = 'https://api.yelp.com/v3/autocomplete?text=' + text;
    axios.get(url, {
        headers: {
            'Authorization' : 'Bearer 5WB5xymf0iQwlxcHxNpE1aZT3sqvyKNELqwbePGxfuAHFGJ-0FsQYcXZ_gmbMvT38VN3b5TGMwm-LzqKrL8IcyGP-dHpdf-hOMgcsPhthKBZGmkX3G35fSHKULg3Y3Yx'
        }
    }).then(response => {                       // this is an axios response object (https://github.com/axios/axios#response-schema)
            console.log(response.data);           // this is the response body from tiltify (https://tiltify.github.io/api/endpoints/campaigns-id-donations.html)
            res.send(response.data);
            res.end();
    }).catch(error => {
            console.log(error);
    });
})

app.get("/searchBusinessDetail", (req, res) => {
    var businessId = req.query.businessId;
    var url = 'https://api.yelp.com/v3/businesses/' + businessId;
    axios.get(url, {
        headers: {
            'Authorization' : 'Bearer 5WB5xymf0iQwlxcHxNpE1aZT3sqvyKNELqwbePGxfuAHFGJ-0FsQYcXZ_gmbMvT38VN3b5TGMwm-LzqKrL8IcyGP-dHpdf-hOMgcsPhthKBZGmkX3G35fSHKULg3Y3Yx'
        }
    }).then(response => {                       // this is an axios response object (https://github.com/axios/axios#response-schema)
            console.log(response.data);           // this is the response body from tiltify (https://tiltify.github.io/api/endpoints/campaigns-id-donations.html)
            res.send(response.data);
            res.end();
    }).catch(error => {
            console.log(error);
    });
})

app.get("/review", (req, res) => {
    var businessId = req.query.businessId;
    console.log(businessId);
    var url = 'https://api.yelp.com/v3/businesses/' + businessId + '/reviews';
    console.log(url);
    axios.get(url, {
        headers: {
            'Authorization' : 'Bearer 5WB5xymf0iQwlxcHxNpE1aZT3sqvyKNELqwbePGxfuAHFGJ-0FsQYcXZ_gmbMvT38VN3b5TGMwm-LzqKrL8IcyGP-dHpdf-hOMgcsPhthKBZGmkX3G35fSHKULg3Y3Yx'
        }
    }).then(response => {                       // this is an axios response object (https://github.com/axios/axios#response-schema)
            console.log(response.data);           // this is the response body from tiltify (https://tiltify.github.io/api/endpoints/campaigns-id-donations.html)
            res.send(response.data);
            res.end();
    }).catch(error => {
            console.log(error);
    });
})

// app.listen(3030, () => console.log('Example app listening on port 3030!'))

const PORT = process.env.PORT || 8080;
app.listen(PORT, () => console.log('Example app listening on port PORT!'))

