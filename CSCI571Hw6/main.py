from flask import Flask, redirect, url_for, jsonify, request, json
import requests
from flask_cors import CORS


app = Flask(__name__)
CORS(app)

@app.route('/')
def index():
    return redirect(url_for('static', filename='index.html'))


@app.route('/yelpRequest')
def deal_with_request():
    term = request.args.get('term')
    latitude = request.args.get('latitude')
    longitude = request.args.get('longitude')
    category = request.args.get('categories')
    distance = request.args.get('distance')
    url = "https://api.yelp.com/v3/businesses/search"
    # call Yelp API
    api_key = "5WB5xymf0iQwlxcHxNpE1aZT3sqvyKNELqwbePGxfuAHFGJ-0FsQYcXZ_gmbMvT38VN3b5TGMwm-LzqKrL8IcyGP-dHpdf-hOMgcsPhthKBZGmkX3G35fSHKULg3Y3Yx"
    headers = {'Authorization': 'Bearer %s' % api_key}
    params = {'term': term, 'latitude': latitude, 'longitude': longitude, 'categories': category, 'distance': distance}
    req = requests.get(url, params=params, headers=headers)
    data = req.json()
    return data


@app.route('/businessDetailYelpRequest')
def deal_with_business_detail_request():
    id = request.args.get('id')
    url = "https://api.yelp.com/v3/businesses/" + id
    print(url)
    # call Yelp API
    api_key = "5WB5xymf0iQwlxcHxNpE1aZT3sqvyKNELqwbePGxfuAHFGJ-0FsQYcXZ_gmbMvT38VN3b5TGMwm-LzqKrL8IcyGP-dHpdf-hOMgcsPhthKBZGmkX3G35fSHKULg3Y3Yx"
    headers = {'Authorization': 'Bearer %s' % api_key}
    req = requests.get(url, headers=headers)
    print('The status code is {}'.format(req.status_code))
    data = req.json()
    print(data)
    # call yelp Search
    # step 1 : extract keyword. https://api.yelp.com/v3/businesses/search?term=keyword&location=
    # step 2 : call yelp api.
    # step return response.
    return data


@app.route('/geocoding')
def get_geolocation():
    location = request.args.get('address')
    google_api_key = request.args.get('key');
    url = "https://maps.googleapis.com/maps/api/geocode/json?" + "address=" + location + "&key=" + google_api_key
    # google geocoding api.
    req = requests.get(url)
    data = req.json()
    return data


if __name__ == '__main__':
    app.run(host="localhost", port=8080, debug=True)