function autoDetectSelected() {
    document.getElementById("checkBox").value = true;
    let disabled = document.getElementById("location").disabled;
    if (disabled) {
        document.getElementById("location").disabled = false;
    }
    else {
        document.getElementById("location").disabled = true;
    }
}

function clearData() {
    document.getElementById("keyWord").value = "";
    document.getElementById("distance").value = "";
    document.getElementById("sel").value = "default";
    document.getElementById("location").value = "";
    document.getElementById("searchResult").style.visibility = "hidden";
    document.getElementById("searchResult").innerHTML= "";
    document.getElementById("detail").innerHTML = "";
    document.getElementById("keyWord").removeAttribute("required");
    document.getElementById("distance").removeAttribute("required");
    document.getElementById("location").removeAttribute("required");
}

function searchYelp() {
    let keyWord = document.getElementById("keyWord").value;
    let distance = document.getElementById("distance").value;
    let category = document.getElementById("sel").value;
    let location = document.getElementById("location").value;
    let checkBox = document.getElementById("checkBox").value;
    /*console.log(keyWord);
    console.log(distance);
    console.log(category);
    console.log(location);
    */
   //console.log(checkBox);
   console.log(checkBox == "true");
   category = category.split(" & ");
   let temp = "";
   for (let idx in category) {
       temp += category[idx];
   }
   category = temp
   console.log(category);
   if (checkBox == "true") {
        // using Latitude and Longitude to locate the  current user.
        const my_token = "77d13dbec3db4a";
        let geo_url = new URL("https://ipinfo.io/");
        geo_url += ("?token=") + my_token;
        console.log(geo_url);
        let url = new URL("https://hw6cs571-364019.uw.r.appspot.com//yelpRequest?");
        url += ("term=") + keyWord;
        url += ("&categories=") + category;
        url += ("&distance=") + distance * 1609.34;
        console.log(url);
        sendIpinfoRequest(geo_url, url);
   } else {
        // using the provided location to locate the current user.
        if (keyWord == '' || distance == '' || category == '' || location == '') {
            // if at least one field is empty, then ask user to fill out that field.
            if (keyWord == '') {
                document.getElementById("keyWord").setAttribute("required", "");
            } else if (distance == '') {
                document.getElementById("distance").setAttribute("required", "");
            } else if (location == '') {
                document.getElementById("location").setAttribute("required", "");
            }
        } else {
            // step1 : using Google Geocoding to get the latitude&longitude of a certain loc
            // location = "1600 Amphitheatre Parkway, Mountain View, CA"
            // splitLoc = {"1600 Amphitheatre Parkway", " Mountain View", "CA"}
            let google_api_key = "AIzaSyAII0DbVU9uKZI07_PMYNLyRDeiXXH3a98";
            // convert format form input = "1600 Amphitheatre Parkway, Mountain View, CA"
            // to "1600+Amphitheatre+Parkway+Mountain+View+CA"
            let splitLoc = location.split(',');
            console.log(splitLoc);
            let locationString = "";
            for (let idx in splitLoc) {
                let loc = splitLoc[idx].split(" ");
                console.log(loc);
                 for (let idx in loc) {
                     if (loc[idx] !== '') {
                         locationString = locationString + loc[idx] + "+";
                     }
                 }
            }
            locationString = locationString.slice(0, -1);
            console.log(locationString);
            let googleUrl = new URL("https://hw6cs571-364019.uw.r.appspot.com//geocoding?");
            googleUrl = googleUrl + "address=" + locationString + "&key=" + google_api_key;
            console.log(googleUrl);

            // send request to the backend.
            let url = new URL("https://hw6cs571-364019.uw.r.appspot.com//yelpRequest?");
            //https://cors-anywhere.herokuapp.com/https://api.yelp.com/v3/businesses/search?location=NYC&categories=bars&open_now=true$limit=40&offset=40
            url += ("term=") + keyWord;
            url += ("&categories=") + category;
            url += ("&distance=") + distance * 1609.34;
            console.log(url);
            geocoding(googleUrl, url);
        }
   }
}

function geocoding(googleUrl, url) {
    let req1 = new XMLHttpRequest();
    let response;
    req1.onreadystatechange = function () {
        if (req1.readyState == 4 && req1.status == 200) {
            response = req1.responseText;
            console.log(response)
            let data = JSON.parse(response);
            console.log(data);
            let info = data["results"]["0"];
            console.log(info);
            let detail = info["geometry"]["location"];
            console.log(detail);
            let lat = detail["lat"];
            let lng = detail["lng"];
            console.log(lat);
            console.log(lng);
            url = url + ("&latitude=") + lat + ("&longitude=") + lng;
            console.log(url);
            sendYelpRequest(url);// send request to backend with required term&location&distance...
        }
    };
    req1.open("GET",googleUrl, true);
    req1.setRequestHeader('Content-Type', 'application/blahblah');
    req1.setRequestHeader('Accept', 'application/blahblah');
    req1.setRequestHeader('Access-Control-Allow-Origin', '*',);
    req1.send();
}

function sendYelpRequest(url) {
    let req = new XMLHttpRequest();
    let response;
    req.onreadystatechange = function () {
        if (req.readyState == 4 && req.status == 200) {
            response = req.responseText;
            console.log(response)
            dealWithYelpResponse(response) // get json form response from the backend and deal with it.
        }
    };
    req.open("GET",url, true);
    req.setRequestHeader('Content-Type', 'application/blahblah');
    req.setRequestHeader('Accept', 'application/blahblah');
    //req.setRequestHeader("Authorization", "Bearer 5WB5xymf0iQwlxcHxNpE1aZT3sqvyKNELqwbePGxfuAHFGJ-0FsQYcXZ_gmbMvT38VN3b5TGMwm-LzqKrL8IcyGP-dHpdf-hOMgcsPhthKBZGmkX3G35fSHKULg3Y3Yx");
    req.setRequestHeader('Access-Control-Allow-Origin', '*',);
    req.send();
}

function dealWithYelpResponse(res) {
    var data = JSON.parse(res);
    console.log(data);
    // remove last query's data
    document.getElementById("searchResult").style.visibility = "visible";
    createTable(data);
}

function sendIpinfoRequest(geo_url, url) {
    let req = new XMLHttpRequest();
    req.onload = function() {
        let data = JSON.parse(req.responseText);
        console.log(data);
        let loc = data["loc"].split(",");
        console.log(loc[0]);
        console.log(loc[1]);
        url += ("&latitude=") + loc[0];
        url += ("&longitude=") + loc[1];
        console.log(url);
        sendYelpRequest(url);
    }
    req.open("GET", geo_url, true);
    req.setRequestHeader('Content-Type', 'application/blahblah');
    req.setRequestHeader('Accept', 'application/blahblah');
    req.setRequestHeader('Access-Control-Allow-Origin', '*',);
    req.send();
}

function createTable(data) {
    const result = [ ];
    let count = 0;
    for (let index in data) {
        let curObj = data[index];
        console.log(index);
        result[count++] = curObj;
    }
    const resturantInfo = result[0];
    console.log(resturantInfo.length);
    // create Table
    var div = document.getElementById("searchResult");
    div.style.visibility = "visible";
    var table = document.createElement("table");
    table.setAttribute("border", "1");
    table.id = "myTable";
    var thead = document.createElement("thead");
    var thead_tr = document.createElement("tr");
    var th1 = document.createElement("th");
    var th2 = document.createElement("th");
    var th3 = document.createElement("th");
    var th4 = document.createElement("th");
    var th5 = document.createElement("th");
    th1.innerText = "NO.";
    th1.className = "sortTag";
    th2.innerText = "Image";
    th3.innerText = "Business Name";
    th3.onclick = sortBusinessName;
    th3.className = "sortTag";
    th4.innerText = "Rating";
    th4.onclick = sortRating;
    th4.className = "sortTag";
    th5.innerText = "Distance(miles)";
    th5.onclick = sortDistance;
    th5.className = "sortTag";
    thead_tr.appendChild(th1);
    thead_tr.appendChild(th2);
    thead_tr.appendChild(th3);
    thead_tr.appendChild(th4);
    thead_tr.appendChild(th5);
    thead.appendChild(thead_tr);
    table.appendChild(thead);
    var tbody = document.createElement("tbody");
    tbody.id = "body";
    tbody.className = "tableBody"
    table.appendChild(tbody);
    div.appendChild(table);
    // create the information in the table.
    for (var idx in resturantInfo) {
        var info = resturantInfo[idx]; // get the idx-th of the list.
        var tr = document.createElement("tr");

        // For the No. column
        var text = idx;
        var num = document.createElement("td");
        num.innerText = text;
        tr.appendChild(num);

        // For the image column
        var img = info["image_url"];
        var im = document.createElement("td");
        var pic = document.createElement("img");
        pic.src = img;
        pic.width = 150;
        pic.height = 150;
        im.appendChild(pic);
        tr.appendChild(im);

        // For the business name column
        var name = info["name"];
        let key = document.createElement("td");
        // let businessName = document.createElement("span");
        // businessName.innerText = name;
        // businessName.className = "restDetail";
        // businessName.id = info["id"];
        // console.log(businessName.innerText);
        // businessName.onclick = businessDetail;
        key.innerText = name;
        key.className = "restDetail1";
        key.id = info["id"];
        key.onclick = businessDetail;
        // key.appendChild(businessName)
        tr.appendChild(key);

        // For Rating column
        var rate = info["rating"];
        var rating = document.createElement("td");
        rating.innerText = rate;
        tr.appendChild(rating);

        // For Distance(miles)
        var distance = info["distance"];
        distance /= 1609.34;
        distance = distance.toFixed(2);
        var dis = document.createElement("td");
        dis.innerText = distance;
        tr.appendChild(dis);
        var bodyTag = document.getElementById("body");
        bodyTag.appendChild(tr);
    }
    window.location.href= '#searchResult';
}

function businessDetail() {
    let url = new URL("http://127.0.0.1:8080/businessDetailYelpRequest?");
    let name = this.id;
    console.log(name);
    url = url + "id=" + name;
    console.log(url);
    sendBusinessDetailRequest(url);
}

function sendBusinessDetailRequest(url) {
    let req = new XMLHttpRequest();
    let response;
    req.onreadystatechange = function () {
        if (req.readyState == 4 && req.status == 200) {
            response = req.responseText;
            dealWithBusinessDetailResponse(response)
        }
    };
    console.log(url);
    req.open("GET", url, true);
    req.setRequestHeader('Content-Type', 'application/blahblah');
    req.setRequestHeader('Accept', 'application/blahblah');
    req.setRequestHeader('Access-Control-Allow-Origin', '*',);
    req.send();
}

function dealWithBusinessDetailResponse(response) {
    let jsonObj = JSON.parse(response);
    console.log(jsonObj);
    generateHTML(jsonObj);
}

function generateHTML(jsonObj) {
    let content = document.getElementById("detail");
    content.innerHTML = " ";
    content.style.visibility = "visible";
    let status = jsonObj["hours"]["0"]["is_open_now"];
    console.log(status);
    let categories = jsonObj["categories"];
    let add = jsonObj["location"]["display_address"];
    let phone = jsonObj["phone"];
    let transactions_supported = jsonObj["transactions"];
    let price = jsonObj["price"];
    let moreInfo = jsonObj["url"];

    // deal with category
    let category = "";
    for (let idx in categories) {
        category += categories[idx]["title"] + " | ";
    }
    category = category.slice(0,-3);
    console.log(category);

    // deal with address
    let address = "";
    for (let idx in add) {
        address += add[idx];
    }
    console.log(address);

    // deal with phone number
    phone = phone.slice(5, 12);
    console.log(phone);
    let phoneNumber = "(213) " + phone;
    phone = phoneNumber;

    // deal with transactions
    let trans = "";
    for (let idx in transactions_supported) {
        if (transactions_supported[idx] === "pickup") {
            trans += "PickUp" + " | ";
        } else {
            trans += "Delivery" + " | ";
        }
    }
    trans = trans.slice(0, -3);
    console.log(trans);

    // create business info form
    let businessDetail = document.getElementById("detail");
    let title = document.createElement("div");
    title.innerText = jsonObj["name"];
    title.id = "businessTitle";
    let underline = document.createElement("div");
    underline.className = "line";
    businessDetail.appendChild(title);
    businessDetail.appendChild(underline);
    console.log(jsonObj["name"]);
    let table = document.createElement("table");
    businessDetail.appendChild(table);
    let tbody = document.createElement("tbody");
    table.appendChild(tbody);
    // row 1
    let tr1 = document.createElement("tr");
    let key = document.createElement("td");
    key.innerText = "Status";
    key.className = "boldBlack";
    tr1.appendChild(key);
    key = document.createElement("td");
    key.innerText = "Category";
    key.className = "boldBlack";
    tr1.appendChild(key);
    tbody.appendChild(tr1);

    // row 2
    let tr2 = document.createElement("tr");
    key = document.createElement("td");
    let statusDiv = document.createElement("div");
    key.appendChild(statusDiv);
    statusDiv.id = "statusDiv";
    console.log(status)
    if (status) {
        statusDiv.innerText = "Open Now";
        statusDiv.style.backgroundColor = "green";
    } else {
        statusDiv.innerText = "Closed";
        statusDiv.style.backgroundColor = "red";
    }
    tr2.appendChild(key);
    key = document.createElement("td");
    key.innerText = category;
    key.className = "lightBlack";
    tr2.appendChild(key);
    tbody.appendChild(tr2);

    // row 3
    let tr3 = document.createElement("tr");
    key = document.createElement("td");
    key.innerText = "Address";
    key.className = "boldBlack";
    tr3.appendChild(key);
    key = document.createElement("td");
    key.innerText = "Phone Number";
    key.className = "boldBlack";
    tr3.appendChild(key);
    tbody.appendChild(tr3);

    // row 4
    let tr4 = document.createElement("tr");
    key = document.createElement("td");
    key.innerText = address;
    key.className = "lightBlack";
    tr4.appendChild(key);
    key = document.createElement("td");
    key.innerText = phone;
    key.className = "lightBlack";
    tr4.appendChild(key);
    tbody.appendChild(tr4);

    // row 5
    let tr5 = document.createElement("tr");
    key = document.createElement("td");
    key.innerText = "Transactions Supported";
    key.className = "boldBlack";
    tr5.appendChild(key);
    key = document.createElement("td");
    key.innerText = "Price";
    key.className = "boldBlack";
    tr5.appendChild(key);
    tbody.appendChild(tr5);

    // row 6
    let tr6 = document.createElement("tr");
    key = document.createElement("td");
    key.innerText = trans;
    key.className = "lightBlack";
    tr6.appendChild(key);
    key = document.createElement("td");
    key.innerText = price;
    key.className = "lightBlack";
    tr6.appendChild(key);
    tbody.appendChild(tr6);

    // row 7
    let tr7 = document.createElement("tr");
    key = document.createElement("td");
    key.innerText = "More info";
    key.className = "boldBlack";
    tr7.appendChild(key);
    tbody.appendChild(tr7);

    // row 8
    let tr8 = document.createElement("tr");
    key = document.createElement("td");
    key.className = "lightBlack";
    let infoLink = document.createElement("a");
    infoLink.href = moreInfo;
    infoLink.innerText = "Yelp";
    infoLink.target = "_blank";
    console.log(moreInfo);
    key.appendChild(infoLink);
    tr8.appendChild(key);
    tbody.appendChild(tr8);

    // create image
    let img = jsonObj["photos"];
    console.log(img);
    let imgDiv = document.createElement("div");
    imgDiv.className = "imgDiv";
    businessDetail.appendChild(imgDiv);
    // append three pictures.
    for (let idx in img) {
        let key = document.createElement("div")
        let imgBlock = document.createElement("img");
        imgBlock.src = img[idx];
        imgBlock.className = "restImg";
        console.log(img[idx]);
        key.appendChild(imgBlock);
        let info = document.createElement("div");
        let num = parseInt(idx) + 1;
        info.innerText = "photo" + num;
        info.className = "info";
        key.appendChild(info);
        imgDiv.appendChild(key);
    }
    window.location.href= '#detail';
    let emptyDiv = document.createElement("div");
    emptyDiv.id = "emptyDiv";
    businessDetail.appendChild(emptyDiv);
}

function sortBusinessName() {
    var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
    table = document.getElementById("myTable");
    switching = true;
    // Set the sorting direction to ascending:
    dir = "asc";
    /* Make a loop that will continue until
    no switching has been done: */
    while (switching) {
        // Start by saying: no switching is done:
        switching = false;
        rows = table.rows;
        /* Loop through all table rows (except the
        first, which contains table headers): */
        for (i = 1; i < (rows.length - 1); i++) {
            // Start by saying there should be no switching:
            shouldSwitch = false;
            /* Get the two elements you want to compare,
            one from current row and one from the next: */
            x = rows[i].getElementsByTagName("TD")[2];
            y = rows[i + 1].getElementsByTagName("TD")[2];
            /* Check if the two rows should switch place,
            based on the direction, asc or desc: */
            if (dir == "asc") {
                if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                    // If so, mark as a switch and break the loop:
                    shouldSwitch = true;
                    break;
                }
            } else if (dir == "desc") {
                if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
                    // If so, mark as a switch and break the loop:
                    shouldSwitch = true;
                    break;
                }
            }
        }
        if (shouldSwitch) {
            /* If a switch has been marked, make the switch
            and mark that a switch has been done: */
            rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
            switching = true;
            // Each time a switch is done, increase this count by 1:
            switchcount++;
        } else {
            /* If no switching has been done AND the direction is "asc",
            set the direction to "desc" and run the while loop again. */
            if (switchcount == 0 && dir == "asc") {
                dir = "desc";
                switching = true;
            }
        }
    }
}

function sortRating() {
  var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
  table = document.getElementById("myTable");
  switching = true;
  //Set the sorting direction to ascending:
  dir = "asc";
  /*Make a loop that will continue until
  no switching has been done:*/
  while (switching) {
    //start by saying: no switching is done:
    switching = false;
    rows = table.rows;
    /*Loop through all table rows (except the
    first, which contains table headers):*/
    for (i = 1; i < (rows.length - 1); i++) {
      //start by saying there should be no switching:
      shouldSwitch = false;
      /*Get the two elements you want to compare,
      one from current row and one from the next:*/
      x = rows[i].getElementsByTagName("TD")[3];
      y = rows[i + 1].getElementsByTagName("TD")[3];
      /*check if the two rows should switch place,
      based on the direction, asc or desc:*/
      if (dir === "asc") {
        if (Number(x.innerHTML) > Number(y.innerHTML)) {
          //if so, mark as a switch and break the loop:
          shouldSwitch= true;
          break;
        }
      } else if (dir === "desc") {
        if (Number(x.innerHTML) < Number(y.innerHTML)) {
          //if so, mark as a switch and break the loop:
          shouldSwitch = true;
          break;
        }
      }
    }
    if (shouldSwitch) {
      /*If a switch has been marked, make the switch
      and mark that a switch has been done:*/
      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
      switching = true;
      //Each time a switch is done, increase this count by 1:
      switchcount ++;
    } else {
      /*If no switching has been done AND the direction is "asc",
      set the direction to "desc" and run the while loop again.*/
      if (switchcount === 0 && dir === "asc") {
        dir = "desc";
        switching = true;
      }
    }
  }
}

function sortDistance() {
  var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
  table = document.getElementById("myTable");
  switching = true;
  //Set the sorting direction to ascending:
  dir = "asc";
  /*Make a loop that will continue until
  no switching has been done:*/
  while (switching) {
    //start by saying: no switching is done:
    switching = false;
    rows = table.rows;
    /*Loop through all table rows (except the
    first, which contains table headers):*/
    for (i = 1; i < (rows.length - 1); i++) {
      //start by saying there should be no switching:
      shouldSwitch = false;
      /*Get the two elements you want to compare,
      one from current row and one from the next:*/
      x = rows[i].getElementsByTagName("TD")[4];
      y = rows[i + 1].getElementsByTagName("TD")[4];
      /*check if the two rows should switch place,
      based on the direction, asc or desc:*/
      if (dir === "asc") {
        if (Number(x.innerHTML) > Number(y.innerHTML)) {
          //if so, mark as a switch and break the loop:
          shouldSwitch= true;
          break;
        }
      } else if (dir === "desc") {
        if (Number(x.innerHTML) < Number(y.innerHTML)) {
          //if so, mark as a switch and break the loop:
          shouldSwitch = true;
          break;
        }
      }
    }
    if (shouldSwitch) {
      /*If a switch has been marked, make the switch
      and mark that a switch has been done:*/
      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
      switching = true;
      //Each time a switch is done, increase this count by 1:
      switchcount ++;
    } else {
      /*If no switching has been done AND the direction is "asc",
      set the direction to "desc" and run the while loop again.*/
      if (switchcount === 0 && dir === "asc") {
        dir = "desc";
        switching = true;
      }
    }
  }
}


/*
<html>
    <head>

    </head>
    <body>

    </body>
</html>
*/
