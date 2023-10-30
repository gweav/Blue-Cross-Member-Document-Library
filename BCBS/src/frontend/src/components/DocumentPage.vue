<script setup>
//<iframe id="serviceFrameSend" src="/EmbeddedApplicationTest.vue" width="1000" height="1000"  frameborder="0"></iframe>
//import "bootstrap/dist/css/bootstrap.css"
</script>

<template>
  <nav class="navbar navbar-expand-lg sticky-top" id="topNavbar">
    <div class="container-fluid">
      <a class="navbar-brand" href="#">
        <img src="./icons/BCBSNCBlueConnect.png" width="275" height="50" alt="">
      </a>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        </ul>
        <ul class="navbar-nav ml-auto">
          <li class="nav-item">
            <a class="nav-link" href="#">
              <img src="./icons/Mail.png" width="50" height="45" alt="">
            </a>
          </li>
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
              <img src="./icons/UserLogo.png" width="50" height="45" alt="">
            </a>
            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
              <li><a class="dropdown-item" href="#">Action</a></li>
              <li><a class="dropdown-item" href="#">Another action</a></li>
              <li><hr class="dropdown-divider"></li>
              <li><a class="dropdown-item" href="#">Something else here</a></li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </nav>
  <div class="container-fluid">
    <div class="row flex-nowrap">
      <div class="col-auto col-md-1 col-xl-1 px-sm-1 px-0" id="sidebar">
          <div class="d-flex flex-column align-items-center align-items-sm-start px-3 pt-2 text-white min-vh-100">
              <ul class="nav nav-pills flex-column mb-sm-auto mb-3 align-items-center align-items-sm-start" id="sidebarMenu">
                  <li class="nav-item">
                    <a href="#" class="nav-link align-middle px-0">
                      <i class="fs-4 bi-house"></i> <span class="ms-1 d-none d-sm-inline" id="navText">Home</span>
                    </a>
                  </li>
                  <li class="nav-item">
                    <a href="#" class="nav-link align-middle px-3 mt-1">
                      <img src="./icons/Profile.png" width="40px" height="40px">
                    </a>
                  </li>
                  <li class="nav-item">
                    <a href="#" class="nav-link align-middle px-2 mt-2">
                      <img src="./icons/Coverage.png" width="52px" height="40px">
                    </a>
                  </li>
                  <li class="nav-item">
                    <a href="#" class="nav-link align-middle px-3 mt-2">
                      <img src="./icons/Claims.png" width="38px" height="40px">
                    </a>
                  </li>
                  <li class="nav-item">
                    <a href="#" class="nav-link align-middle px-2 mt-2">
                      <img src="./icons/FindCare.png" width="55px" height="40px">
                    </a>
                  </li>
                  <li>
                    <a href="#" class="nav-link align-middle px-0 mt-2">
                      <img src="./icons/Documents.png" width="70px" height="40px">
                    </a>
                  </li>
                  <li>
                    <a href="#" class="nav-link align-middle px-1 mt-2">
                      <img src="./icons/Wellbeing.png" width="60px" height="42px">
                    </a>
                  </li>
                  <li>
                    <a href="#" class="nav-link align-middle px-2 mt-2">
                      <img src="./icons/ActivityCenter.png" width="45px" height="50px">
                    </a>
                  </li>
                  <li>
                    <a href="#" class="nav-link align-middle px-2 mt-2">
                      <img src="./icons/Logout.png" width="45px" height="40px">
                    </a>
                  </li>
              </ul>
            <hr>
        </div>
      </div>
      <div class="col" id="backgroundColorLightGrey">
        <div class="container-fluid" id="contentArea">
          <div id="InitialMessage">
            <span>You can view and search important communications and documents related to your plan in the Document Library. 
              More Documents will be added as they become available
              <br>
              <br>
              <strong>Note:</strong> Documents visible in your Document Library comply with
              <a style="text-decoration:none" href="https://www.bluecrossnc.com/about-us/policies-and-best-practices/privacy-policy">
                <strong id="TextLink">Blue Cross NC's Privacy Policy.</strong>
              </a>
            </span>
          </div>
          <div class="container-fluid" id="deliveryPreferences">
            <img src="./icons/Leaf.png" id="leaf">
            <strong id="Text-PaperlessDelivery"> Paperless Delivery</strong>
            <br>
            Receive electronic communications instead of paper documents related to your account. If you have not updated your preferences, you may do so anytime
            <br>
            <br>
            <p class="text-center">
              <a style="text-decoration:none" href="#">
                <strong id="TextLink">Update Delivery Preferences</strong>
              </a>
            </p>
          </div>

          <hr>

          <div class="row">
            <div class="container" id="SearchForDocumentText">
              <strong id="Text-PaperlessDelivery"> Search for documents</strong>
            </div>
          </div>

          <div class="DocumentSearching">
              <div class="row" id="DocumentSearching">
                <div class="col-7">
                  <div class="input-group">
                    <input type="text" class="form-control" placeholder="Search" aria-label="Search" id="SearchBar">
                    <button class="btn" id="SearchLogo">
                      <img src="./icons/DocumentQueryLogo.png" width="20px" height="20px">
                    </button>
                  </div>
                </div>
                <div class="col-3" id="addFilterPadding">
                  <button class="btn dropdown-toggle" id="filterDropdown" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                    <span id="filterName">Filter By</span>
                  </button>
                  <ul class="dropdown-menu">
                    <li><a class="dropdown-item" id="dropdown-item-filter" href="#">Relevance</a></li>
                    <li><a class="dropdown-item" id="dropdown-item-filter" href="#">Date Issued</a></li>
                    <li><a class="dropdown-item" id="dropdown-item-filter" href="#">Alphabetically</a></li>
                  </ul>
                </div>
              </div>
          </div>

          <div class="filters" id="filters">
            <button class="btn dropdown-toggle" id="buttonDropdown" type="button" data-bs-toggle="dropdown" aria-expanded="false">
              <span id="selectPlanMessage">Select Plan</span>
              <br>
              <span id="filterPlanName">(01/01/2023 - Active)</span>
            </button>
            <ul class="dropdown-menu">
              <li><a class="dropdown-item" id="dropdown-item-plan" href="#">Action</a></li>
              <li><a class="dropdown-item" id="dropdown-item-plan" href="#">Another action</a></li>
              <li><a class="dropdown-item" id="dropdown-item-plan" href="#">Something else here</a></li>
            </ul>
            <button class="btn dropdown-toggle mr-1" id="buttonDropdown" type="button" data-bs-toggle="dropdown" aria-expanded="false">
              <span id="selectTypeMessage">Document Type</span>
              <br>
              <span id="filterTypeName">All</span>
            </button>
            <ul class="dropdown-menu">
              <li><a class="dropdown-item" id="dropdown-item-type" href="#">Action</a></li>
              <li><a class="dropdown-item" id="dropdown-item-type" href="#">Another action</a></li>
              <li><a class="dropdown-item" id="dropdown-item-type" href="#">Something else here</a></li>
            </ul>
            <button class="btn dropdown-toggle mr-1" id="buttonDropdown" type="button" data-bs-toggle="dropdown" aria-expanded="false">
              <span id="selectDependentMessage">Dependents</span>
              <br>
              <span id="filterDependentName">All</span>
            </button>
            <ul class="dropdown-menu">
              <li><a class="dropdown-item" id="dropdown-item-dependent" href="#">Action</a></li>
              <li><a class="dropdown-item" id="dropdown-item-dependent" href="#">Another action</a></li>
              <li><a class="dropdown-item" id="dropdown-item-dependent" href="#">Something else here</a></li>
            </ul>
            <button class="btn dropdown-toggle mr-1" id="buttonDropdownDate" type="button" data-bs-toggle="dropdown" aria-expanded="false">
              <img src="./icons/Calendar.png" width="20px" height="20px">
              <span id="filterDate">Date</span>
            </button>
            <ul class="dropdown-menu">
              <li><a class="dropdown-item" id="dropdown-item-date" href="#">Action</a></li>
              <li><a class="dropdown-item" id="dropdown-item-date" href="#">Another action</a></li>
              <li><a class="dropdown-item" id="dropdown-item-date" href="#">Something else here</a></li>
            </ul>
            <a style="text-decoration:none" href="#">
              <strong id="TextLink"> &nbsp &nbspClear Filters</strong>
            </a>
          </div>
          <div class="DocumentTable" id="documentTable">
            <table class="table" id="tableContents">
              <thead>
                <tr>
                  <th scope="col">Type</th>
                  <th scope="col">Name</th>
                  <th scope="col">Date Issued</th>
                  <th scope="col"></th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <th scope="row">
                    <button class="btn" id="typePillsEOB">
                      EOB
                    </button>
                  </th>
                  <td>
                    <img src="./icons/DocumentSearchLogo.png" width="20px" height="20px">
                    <a style="text-decoration:none" href="#"><span id="docName"> Explanation of Benefits</span></a>
                  </td>
                  <td>01/17/2023</td>
                  <td>
                    <a style="text-decoration:none" href="#">
                      <strong id="TextLink">Download  </strong><img src="./icons/DownloadSymbol.png" width="12px" height="12px">
                    </a>
                  </td>
                </tr>
                <tr>
                  <th scope="row">
                    <button class="btn" id="typePillsCoverage">
                      Coverage
                    </button>
                  </th>
                  <td>
                    <img src="./icons/DocumentSearchLogo.png" width="20px" height="20px">
                    <a style="text-decoration:none" href="#"><span id="docName"> Summary of Benefits and Coverage (2023) </span></a>
                  </td>
                  <td>12/31/2022</td>
                  <td>
                    <a style="text-decoration:none" href="#">
                      <strong id="TextLink">Download  </strong><img src="./icons/DownloadSymbol.png" width="12px" height="12px">
                    </a>
                  </td>
                </tr>
                <tr>
                  <th scope="row">
                    <button class="btn" id="typePillsEOB">
                      EOB
                    </button>
                  </th>
                  <td>
                    <img src="./icons/DocumentSearchLogo.png" width="20px" height="20px">
                    <a style="text-decoration:none" href="#"><span id="docName"> Explanation of Benefits</span></a>
                  </td>
                  <td>12/29/2022</td>
                  <td>
                    <a style="text-decoration:none" href="#">
                      <strong id="TextLink">Download  </strong><img src="./icons/DownloadSymbol.png" width="12px" height="12px">
                    </a>
                  </td>
                </tr>
                <tr>
                  <th scope="row">
                    <button class="btn" id="typePillsEOB">
                      EOB
                    </button>
                  </th>
                  <td>
                    <img src="./icons/DocumentSearchLogo.png" width="20px" height="20px">
                    <a style="text-decoration:none" href="#"><span id="docName"> Explanation of Benefits</span></a>
                  </td>
                  <td>12/29/2022</td>
                  <td>
                    <a style="text-decoration:none" href="#">
                      <strong id="TextLink">Download  </strong><img src="./icons/DownloadSymbol.png" width="12px" height="12px">
                    </a>
                  </td>
                </tr>
                <tr>
                  <th scope="row">
                    <button class="btn" id="typePillsBill">
                      Bill
                    </button>
                  </th>
                  <td>
                    <img src="./icons/DocumentSearchLogo.png" width="20px" height="20px">
                    <a style="text-decoration:none" href="#"><span id="docName"> Plan Bill - (Medical) (January)</span></a>
                  </td>
                  <td>12/28/2022</td>
                  <td>
                    <a style="text-decoration:none" href="#">
                      <strong id="TextLink">Download  </strong><img src="./icons/DownloadSymbol.png" width="12px" height="12px">
                    </a>
                  </td>
                </tr>
                <tr>
                  <th scope="row">
                    <button class="btn" id="typePillsEOB">
                      EOB
                    </button>
                  </th>
                  <td>
                    <img src="./icons/DocumentSearchLogo.png" width="20px" height="20px">
                    <a style="text-decoration:none" href="#"><span id="docName"> Explanation of Benefits</span></a>
                  </td>
                  <td>12/23/2022</td>
                  <td>
                    <a style="text-decoration:none" href="#">
                      <strong id="TextLink">Download  </strong><img src="./icons/DownloadSymbol.png" width="12px" height="12px">
                    </a>
                  </td>
                </tr>
                <tr>
                  <th scope="row">
                    <button class="btn" id="typePillsEOB">
                      EOB
                    </button>
                  </th>
                  <td>
                    <img src="./icons/DocumentSearchLogo.png" width="20px" height="20px">
                    <a style="text-decoration:none" href="#"><span id="docName"> Explanation of Benefits</span></a>
                  </td>
                  <td>12/22/2022</td>
                  <td>
                    <a style="text-decoration:none" href="#">
                      <strong id="TextLink">Download  </strong><img src="./icons/DownloadSymbol.png" width="12px" height="12px">
                    </a>
                  </td>
                </tr>
                <tr>
                  <th scope="row">
                    <button class="btn" id="typePillsEOB">
                      EOB
                    </button>
                  </th>
                  <td>
                    <img src="./icons/DocumentSearchLogo.png" width="20px" height="20px">
                    <a style="text-decoration:none" href="#"><span id="docName"> Explanation of Benefits</span></a>
                  </td>
                  <td>12/20/2022</td>
                  <td>
                    <a style="text-decoration:none" href="#">
                      <strong id="TextLink">Download  </strong><img src="./icons/DownloadSymbol.png" width="12px" height="12px">
                    </a>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          <nav aria-label="Page navigation example" id="DocumentsPagination">
            <ul class="pagination justify-content-end">
              <li class="page-item disabled">
                <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Previous</a>
              </li>
              <li class="page-item"><a class="page-link" href="#">1</a></li>
              <li class="page-item"><a class="page-link" href="#">2</a></li>
              <li class="page-item"><a class="page-link" href="#">3</a></li>
              <li class="page-item">
                <a class="page-link" href="#">Next</a>
              </li>
            </ul>
          </nav>
        </div>
      </div>
    </div>
  </div>
  {{ patient }}
  {{ plans }}
  {{ documents }}



   <!-- <tr>
                  <th scope="row">
                    <button class="btn" id="typePillsCoverage">
                      Coverage
                    </button>
                  </th>
                  <td>
                    <img src="./icons/DocumentSearchLogo.png" width="20px" height="20px">
                    <a style="text-decoration:none" href="#"><span id="docName"> Summary of Benefits and Coverage (2023) </span></a>
                  </td>
                  <td>12/31/2022</td>
                  <td>
                    <a style="text-decoration:none" href="#">
                      <strong id="TextLink">Download  </strong><img src="./icons/DownloadSymbol.png" width="12px" height="12px">
                    </a>
                  </td>
                </tr>
                <tr>
                  <th scope="row">
                    <button class="btn" id="typePillsEOB">
                      EOB
                    </button>
                  </th>
                  <td>
                    <img src="./icons/DocumentSearchLogo.png" width="20px" height="20px">
                    <a style="text-decoration:none" href="#"><span id="docName"> Explanation of Benefits</span></a>
                  </td>
                  <td>12/29/2022</td>
                  <td>
                    <a style="text-decoration:none" href="#">
                      <strong id="TextLink">Download  </strong><img src="./icons/DownloadSymbol.png" width="12px" height="12px">
                    </a>
                  </td>
                </tr>
                <tr>
                  <th scope="row">
                    <button class="btn" id="typePillsEOB">
                      EOB
                    </button>
                  </th>
                  <td>
                    <img src="./icons/DocumentSearchLogo.png" width="20px" height="20px">
                    <a style="text-decoration:none" href="#"><span id="docName"> Explanation of Benefits</span></a>
                  </td>
                  <td>12/29/2022</td>
                  <td>
                    <a style="text-decoration:none" href="#">
                      <strong id="TextLink">Download  </strong><img src="./icons/DownloadSymbol.png" width="12px" height="12px">
                    </a>
                  </td>
                </tr>
                <tr>
                  <th scope="row">
                    <button class="btn" id="typePillsBill">
                      Bill
                    </button>
                  </th>
                  <td>
                    <img src="./icons/DocumentSearchLogo.png" width="20px" height="20px">
                    <a style="text-decoration:none" href="#"><span id="docName"> Plan Bill - (Medical) (January)</span></a>
                  </td>
                  <td>12/28/2022</td>
                  <td>
                    <a style="text-decoration:none" href="#">
                      <strong id="TextLink">Download  </strong><img src="./icons/DownloadSymbol.png" width="12px" height="12px">
                    </a>
                  </td>
                </tr>
                <tr>
                  <th scope="row">
                    <button class="btn" id="typePillsEOB">
                      EOB
                    </button>
                  </th>
                  <td>
                    <img src="./icons/DocumentSearchLogo.png" width="20px" height="20px">
                    <a style="text-decoration:none" href="#"><span id="docName"> Explanation of Benefits</span></a>
                  </td>
                  <td>12/23/2022</td>
                  <td>
                    <a style="text-decoration:none" href="#">
                      <strong id="TextLink">Download  </strong><img src="./icons/DownloadSymbol.png" width="12px" height="12px">
                    </a>
                  </td>
                </tr>
                <tr>
                  <th scope="row">
                    <button class="btn" id="typePillsEOB">
                      EOB
                    </button>
                  </th>
                  <td>
                    <img src="./icons/DocumentSearchLogo.png" width="20px" height="20px">
                    <a style="text-decoration:none" href="#"><span id="docName"> Explanation of Benefits</span></a>
                  </td>
                  <td>12/22/2022</td>
                  <td>
                    <a style="text-decoration:none" href="#">
                      <strong id="TextLink">Download  </strong><img src="./icons/DownloadSymbol.png" width="12px" height="12px">
                    </a>
                  </td>
                </tr>
                <tr>
                  <th scope="row">
                    <button class="btn" id="typePillsEOB">
                      EOB
                    </button>
                  </th>
                  <td>
                    <img src="./icons/DocumentSearchLogo.png" width="20px" height="20px">
                    <a style="text-decoration:none" href="#"><span id="docName"> Explanation of Benefits</span></a>
                  </td>
                  <td>12/20/2022</td>
                  <td>
                    <a style="text-decoration:none" href="#">
                      <strong id="TextLink">Download  </strong><img src="./icons/DownloadSymbol.png" width="12px" height="12px">
                    </a>
                  </td>
                </tr> -->
 
</template>

<script>
export default {
  props: {
    user: String
  },
  data() {
    return {
      patient: "",
      plans: "",
      documents: []

    }
  },

  mounted() {
    fetch("http://localhost:3000/patients")
      .then((res) => res.json())
      .then((patients) => {
        const patient = patients.find((patient) => patient.name === this.user);
        if (patient) {
          this.patient = patient;
          return fetch("http://localhost:3000/plans/" + patient.Plans);
        }
      })
      .then((res) => res.json())
      .then((plan) => {
        this.plans = plan;
        return fetch("http://localhost:3000/documents");
      })
      .then((res) => res.json())
      .then((documents) => {
        this.documents = documents.filter((document) =>
          this.plans.documents.includes(document.id)
        );
      });
  },
        
}

</script>

<style>


html, body {
  margin:0;
  padding:0;
  height:100%;
  background-color: lightgrey;
}
#outPopUp {
  position: absolute;

  width : 700px;
  height: 400px;
  top: 50%;
  left: 50%;
  margin: -100px 0 0 -350px;
  
}

#topNavbar {
  background-color:white;
}

#sidebar {
  background-color: #133258;
}

#navText {
  color:white;
}

#SearchBar {
  padding-top:10px;
  padding-bottom:10px;
  margin-top:15px;
}

#SearchLogo {
  margin-top:15px;
  border: 1px solid;
  border-color:lightgrey;
}

#backgroundColorLightGrey {
  background-color:lightgrey;
}

#leaf {
  width:50px;
}

#InitialMessage {
  padding-bottom:30px;
}

#contentArea {
  background-color:white;
  margin-top:50px;
  padding-top:10px;
  padding-bottom:10px;
  padding-left:20px;
  padding-right:20px;
}

#deliveryPreferences {
  padding-top: 10px;
  padding-bottom: 2px;
  background-color:#e8ecf7;
}

#SearchForDocumentText {
  margin-left:10px;
}

#DocumentSearching {
  height:100px;
}

#buttonDropdown {
  border: 2px solid;
  padding-top:0px;
  border-radius: 4px;
  border-color: lightgrey;
  margin-right:25px;
}

#filterDropdown {
  margin-top:15px;
  margin-bottom:50px;
  border: 2px solid;
  padding-top:0px;
  border-radius: 4px;
  border-color: lightgrey;
  height:50px;
}

#removeSearchPadding {
  padding-left:0px;
}

#addFilterPadding {
  padding-left:58px;
}

#buttonDropdownDate {
  border: 2px solid;
  padding-top:15px;
  padding-bottom:15px;
  border-radius: 4px;
  border-color: lightgrey;
  margin-right:10px;
}

#selectFilterMessage {
  font-size:10px;
  text-align: left;
  margin-right:50px;
}

#filterName {
  margin-right:160px;
}

#dropdown-item-filter {
  margin-right:125px;
}

#selectPlanMessage {
  font-size:10px;
  text-align: left;
  margin-right:260px;
}

#filterPlanName {
  text-align: left;
  margin-right:150px;
}

#dropdown-item-plan {
  margin-right:310px;
}

#selectTypeMessage {
  font-size:10px;
  text-align: left;
  margin-right:110px;
}

#selectDependentMessage {
  font-size:10px;
  text-align: left;
  margin-right:130px;
}

#filterTypeName {
  text-align: left;
  margin-right:150px;
}

#filterDependentName {
  text-align: left;
  margin-right:150px;
}

#filters {
  margin-bottom:30px;
}

#dropdown-item-type {
  margin-right:80px;
}

#dropdown-item-dependent {
  margin-right:30px;
}

#filterDate {
  margin-left:10px;
  margin-right:150px;
}

#dropdown-item-date {
  margin-right:75px;
}

#TextLink {
  color:#3e79c1
}

#Text-PaperlessDelivery {
  font-size:20px;
}

#documentTable {
  border:1px solid;
  border-color:lightgrey;
}

th {
  color:rgb(179, 178, 178);
}

#docName {
  color:#3e79c1;
}

#typePillsEOB {
  background-color:black;
  border-radius:30px;
  height:30px;
  color:lightgrey;
  padding-top:0px;
}

#typePillsCoverage {
  background-color:#133258;
  border-radius:30px;
  height:30px;
  color:lightgrey;
  padding-top:0px;
}

#typePillsBill {
  background-color:green;
  border-radius:30px;
  height:30px;
  color:lightgrey;
  padding-top:0px;
}

#DocumentsPagination {
  margin-top:20px;
}

</style>