<script setup>
//<iframe id="serviceFrameSend" src="/EmbeddedApplicationTest.vue" width="1000" height="1000"  frameborder="0"></iframe>
//import "bootstrap/dist/css/bootstrap.css"
</script>

<template>
  <!-- Top navigation bar -->
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
            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown"
              aria-expanded="false">
              <!-- <img src="./icons/UserLogo.png" width="50" height="45" alt=""> -->
              <button class="btn" id="typePillsUser">{{ initials }} </button>

            </a>
            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
              <li><a class="dropdown-item" href="#">Action</a></li>
              <li><a class="dropdown-item" href="#">Another action</a></li>
              <li>
                <hr class="dropdown-divider">
              </li>
              <li><a class="dropdown-item" href="#">Something else here</a></li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </nav>
  <!-- Sidebar navigation -->
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
      <!-- Main content area -->
      <div id="backgroundColorLightGrey">
        <div class="container-fluid overflow-hidden" id="contentArea">
          <!-- Initial message section -->
          <div id="InitialMessage">
            <span>You can view and search important communications and documents related to your plan in the Document
              Library.
              More Documents will be added as they become available
              <br>
              <br>
              <strong>Note:</strong> Documents visible in your Document Library comply with
              <a style="text-decoration:none"
                href="https://www.bluecrossnc.com/about-us/policies-and-best-practices/privacy-policy">
                <strong id="TextLink">Blue Cross NC's Privacy Policy.</strong>
              </a>
            </span>
          </div>
          <!-- Delivery preferences section -->
          <div class="container-fluid" id="deliveryPreferences">
            <img src="./icons/Leaf.png" id="leaf">
            <strong id="Text-PaperlessDelivery"> Paperless Delivery</strong>
            <br>
              Receive electronic communications instead of paper documents related to your account. If you have not updated
              your preferences, you may do so anytime
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
            <!-- Search for documents section -->
            <div class="container" id="SearchForDocumentText">
              <strong id="Text-PaperlessDelivery"> Search for documents</strong>
            </div>
          </div>
          <!-- Document searching section -->
          <div class="DocumentSearching">
            <div class="row" id="DocumentSearching">
              <div class="col-9">
                <div class="input-group">
                  <input v-model="searchText"  type="text" class="form-control" placeholder="Search"
                    aria-label="Search" id="SearchBar">
                  <button class="btn" id="SearchLogo" @click="search">

                    <span id="magGlass">&#x1F50D;</span>
                  </button>
                </div>
              </div>
            </div>
            <div class="row filters" id="filters">
              <div class="col-3" id="filterCol">
                <!-- Policy sorting -->
                <button class="btn col-md-3 dropdown-toggle" id="buttonDropdown" type="button" data-bs-toggle="dropdown"
                  aria-expanded="false">
                  <span id="selectFilterMessage">Select Plan</span>
                  <br>
                  <span id="filterCurrent-Plan">
                    <span>({{ currPlan.year }} - </span>
                    <span v-if="currPlan.active">Active)</span>
                    <span v-else>{{ parseInt(currPlan.year) + 1 }}</span>
                    <span> - {{ currPlan.id }})</span>
                  </span>
                </button>
                <ul class="dropdown-menu" id="PlanDropdown">
                  <li v-for="policy in policies" :key="policy.id">
                    <a class="dropdown-item" @click="currPlan = policy, populateDependents(policy)">
                      {{ policy.id }} - {{ policy.year }} ({{ policy.active ? 'Active' : 'Inactive' }})
                    </a>
                  </li>
                </ul>
              </div>
            
              <div class="col-2" id="filterCol">
                <!-- Type sorting -->
                <button class="col-2 btn dropdown-toggle dropdown-toggle-end mr-1 float-end" id="buttonDropdown" type="button" data-bs-toggle="dropdown"
                  aria-expanded="false">
                  <span id="selectFilterMessage">Document Type</span>
                  <br>
                  <span id="filterCurrent-Type">{{ formatDocumentType(type) }}</span>                  
                </button>
                <ul class="dropdown-menu " id="dropdowns">
                  <li><a class="dropdown-item" id="dropdown-item-type" @click="changeType('All')">All</a></li>
                  <li><a class="dropdown-item" id="dropdown-item-type" @click="changeType('EOB')">EOB</a></li>
                  <li><a class="dropdown-item" id="dropdown-item-type" @click="changeType('Bill')">Bill</a></li>
                  <li><a class="dropdown-item" id="dropdown-item-type" @click="changeType('Coverage')">Coverage</a></li>
                  <li><a class="dropdown-item" id="dropdown-item-type" @click="changeType('ID Card')">ID Card</a></li>
                  <li><a class="dropdown-item" id="dropdown-item-type" @click="changeType('Enrollment')">Enrollment</a></li>
                  <li><a class="dropdown-item" id="dropdown-item-type" @click="changeType('Letter')">Letter</a></li>
    
                </ul>
              </div>
              <div class="col-2" id="filterCol">
                <!-- Member sorting -->
                <button class="col-md-2 btn dropdown-toggle mr-1" ref="dependentdropdown" id="buttonDropdown" type="button"
                  data-bs-toggle="dropdown" aria-expanded="false" :disabled="dependents.length === 0 || !isSubscriber">
                  <span id="selectFilterMessage">Members</span>
                  <br>
                  <span  v-if="dependents.length === 0 && isSubscriber">No associated dependents</span>
                  <span v-else ref="dependentSelected" id="filterCurrent-Members">
                    {{ (currentMember === "All" ? (isSubscriber ? "All" : "Cannot view other members") : currentMember.name) }}
                  </span>
                </button>
                <ul class="col-2 dropdown-menu">
                  <li v-if="isSubscriber">
                    <a class="dropdown-item" id="dropdown-item-dependent"
                      @click="getAllPatientDocs(), currentMember = 'All'">All</a>
                  </li>
                  <li v-if="isSubscriber">
                    <a class="dropdown-item" id="dropdown-item-dependent"
                      @click="getPatientDocs(patient.id), currentMember = patient">
                      {{ patient.name }} - {{ calculateAge(patient.dob) }}
                    </a>
                  </li>
                  <li v-for="dependent in dependents" :key="dependent.id" v-if="isSubscriber">
                    <a class="dropdown-item" id="dropdown-item-dependent"
                      @click="getPatientDocs(dependent.id), currentMember = dependent">
                      {{ dependent.name }} - {{ calculateAge(dependent.dob) }}
                    </a>
                  </li>
                </ul>
              </div>
              <div class="col-2" id="filterCol">
                <!-- Sort by date -->
                <input v-model="dateFiltered" id="startDate" class="form-control" type="date" />
                <ul class="dropdown-menu">
                  <li><a class="dropdown-item" id="dropdown-item-date" href="#">Action</a></li>
                  <li><a class="dropdown-item" id="dropdown-item-date" href="#">Another action</a></li>
                  <li><a class="dropdown-item" id="dropdown-item-date" href="#">Something else here</a></li>
                </ul>
              </div>
              <div class="col-1 d-flex justify-content-center" id="filterCol">
                <!-- clear all filters -->
                <button class="clearButton" @click="clearFilters()">
                  <strong id="ClearFilterMessage">Clear Filter</strong>
                </button>
              </div>
            </div>
          </div>
          <!-- Document table-->
          <div class="table-responsive" id="EntireTable">
            <table class="table" id="tableContents">
              <thead>
                <tr>
                  <th scope="col">Type</th>
                  <th scope="col">Name</th>
                  <th scope="col">
                    Date Issued
                    <button id="DateToggleSorting" @click="toggleSort">
                      <span v-if="sortAscending">&#x25B2;</span> <!-- Up arrow -->
                      <span v-else>&#x25BC;</span> <!-- Down arrow -->
                    </button>
                  </th>
                  <th scope="col"></th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="doc in paginatedDocuments" :key="doc.id">
                  <th scope="row">
                    <button class="btn" :id="'typePills' + formatDocumentType(doc.type).replace(/\s+/g, '')">
                      {{ formatDocumentType(doc.type) }}
                    </button>
                  </th>
                  <td>
                    <img class="DocumentSearchLogo" src="./icons/DocumentSearchLogo.png">
                    <a style="text-decoration:none" href="#"><span id="docName">{{ doc.name }}</span></a>
                  </td>
                  <td>{{ doc.issueDate.split("-")[1] + "/" + doc.issueDate.split("-")[2] + "/" +
                    doc.issueDate.split("-")[0] }}</td>
                  <td>
                    <a style="text-decoration:none" href="#">
                      <strong id="TextLink">Download </strong><img class="DownloadImg" src="./icons/DownloadSymbol.png">
                    </a>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          <!-- Pagination section -->
          <nav aria-label="Page navigation example" id="DocumentsPagination">
            <span id="Message-PaginationMessage">Rows per page :</span>
            <select @change="setPageSize($event.target.value)" class="form-select"
              style="width: 70px; margin-right: 10px;">
              <option value="5">5</option>
              <option value="10" selected>10</option>
              <option value="15">15</option>
              <option value="All">All</option>
            </select>
            <span id="Message-PaginationMessage"> {{ (((currentPage - 1) * perPage) + 1) + "-" + Math.min(currentPage * perPage,
              filteredDocuments.length) + " of " + filteredDocuments.length }}</span>
            <button type="button" class="btn btn-light" id="PaginationPageChangePrevious" @click="prevPage">Previous</button>
            <button type="button" class="btn btn-light" id="PaginationPageChangeNext" @click="nextPage">Next</button>
          </nav>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
export default {
  props: {
    bcbsId: String
  },
  data() {
    return {
      currPlan: [],
      initials: "",
      patient: "",
      dependents: [],
      documents: [],
      currentMember: "All",
      AllPatients: true,
      type: "All",
      dateFiltered: "",
      currentPage: 1,
      perPage: 10,
      searchText: '',
      policies: '',
      sortAscending: true,
      isSubscriber: false,
    }
  },

  created() {
    this.fetchData();
  },

  methods: {
    async fetchData() {
      await fetch(`http://localhost:8080/api/populate`)

      fetch(`http://localhost:8080/api/user/${this.bcbsId}`).then((res) => res.json())
        .then(response => {
          console.log(response)
          this.patient = response;
          this.initials = this.patient.name.split(" ")[0][0] + this.patient.name.split(" ")[1][0]
          this.policies = this.patient.policies
          this.currPlan = this.patient.policies.find(policy => policy.active);
          this.isSubscriber = this.bcbsId === this.currPlan.subscriberId;
          if (this.isSubscriber) {
            this.populateDependents(this.currPlan);
          }
        });

      fetch(`http://localhost:8080/api/user/${this.bcbsId}/documents`).then((res) => res.json())
        .then((documents) => {
          console.log(documents)
          this.documents = documents;
          this.documents = this.sortedDocuments;


        });
    },


    // This function is responsible for populating the array of dependents to be displayed to the user
  // It takes the policy object as a parameter
  // The policy object contains an array of dependent IDs
  // The function iterates through the array of dependent IDs
  // For each dependent ID, it sends a GET request to the server and retrieves the name and date of birth of the dependent
  // It then pushes the name, date of birth, and dependent ID of the dependent into an array of dependents
  // When the iteration is complete, the array of dependents is populated with the names, dates of birth, and dependent IDs of all dependents associated with the policy
  populateDependents(policy) {
    this.clearFilters();
      if (policy) {
        console.log("HERE")
        console.log(this.bcbsId)
        this.dependents = []
        policy.dependents.forEach(dependentId => {
          fetch(`http://localhost:8080/api/user/${dependentId}`).then((res) => res.json())
            .then(res => {
              this.dependents.push({
                name: res.name,
                dob: res.dob,
                id: dependentId
              });
            });
        });
      }
    },
  
// This function reverses the order of the documents array.
toggleSort() {
      this.sortAscending = !this.sortAscending;
      this.documents.reverse();
    },
   
    getPatientDocs(currentMem) {
      this.AllPatients = false;
      this.currentMember = currentMem.id;
      this.currentPage = 1;
    },

    getAllPatientDocs() {
      this.currentMember = "All";
      this.AllPatients = true;
      this.currentPage = 1;
    },

    // This code changes the type of the document when the user selects a new document type
    // from the dropdown menu. It then updates the current page to 1 to show the first page
    // of the new document type.
    changeType(type) {
      if (type === "ID Card") {
        this.type = "ID_CARD"
      }
      else if (type != "All") {
        this.type = type.toUpperCase();
      }
      else {
        this.type = type
      }
      this.currentPage = 1;
    },

    clearFilters() {
      this.dateFiltered = "";
      this.type = "All";
      this.getAllPatientDocs();
    },

    generateTableRows(docs) {
      return docs.filter((doc) =>
        (doc.policyId === this.currPlan.id) &&
        (this.AllPatients ? 1 === 1 : doc.userId === this.currentMember.id) &&
        ((this.type === 'All' && this.dateFiltered === '') ||
          (doc.type === this.type && this.dateFiltered === '') ||
          (this.type === 'All' && this.dateFiltered === doc.date) ||
          (doc.type === this.type && this.dateFiltered === doc.date))
      );
    },

    nextPage() {
      if (this.currentPage * this.perPage < this.filteredDocuments.length) {
        this.currentPage++;
      }
    },

    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
      }
    },

    setPageSize(size) {
      this.perPage = size === "All" ? this.documents.length : size;
      this.currentPage = 1;
      
    },
    calculateAge(dob) {
      const birthDate = new Date(dob);
      const today = new Date();
      let age = today.getFullYear() - birthDate.getFullYear();
      const monthDifference = today.getMonth() - birthDate.getMonth();

      if (monthDifference < 0 || (monthDifference === 0 && today.getDate() < birthDate.getDate())) {
        age--;
      }

      return age;
    },


    formatDocumentType(type) {
      switch (type) {
        case "EOB":
          return "EOB";
        case "COVERAGE":
          return "Coverage";
        case "ID_CARD":
          return "ID Card";
        case "LETTER":
          return "Letter";
        case "ENROLLMENT":
          return "Enrollment";
        case "BILL":
          return "Bill";
        default:
          return type;
      }
    },

    async search() {
      const response = await fetch(`http://localhost:8080/api/user/${this.bcbsId.toLowerCase()}/documents/${this.searchText}`);
      const documents = await response.json();
      this.documents = documents;
    }
  },

  computed: {
    filteredDocuments() {
      console.log(this.documents)
      return this.generateTableRows(this.currentMember === 'All' ? this.documents : this.documents.filter((doc) => doc.userId == this.currentMember.id));
    },

    paginatedDocuments() {
      console.log(this.documents)
      const startIndex = (this.currentPage - 1) * this.perPage;
      const endIndex = this.currentPage * this.perPage;
      console.log(this.filteredDocuments.slice(startIndex, endIndex))
      return this.filteredDocuments.slice(startIndex, endIndex);
    },

    sortedDocuments() {
      return this.documents.slice().sort((a, b) => {
        return new Date(b.issueDate) - new Date(a.issueDate);
      });
    }
  }
}
</script>


<style>
html,
body {
  margin: 0;
  padding: 0;
  height: 100%;
  background-color: lightgrey;
}

/* Navbar Styling */
#topNavbar {
  background-color: white;
}

#sidebar {
  background-color: #133258;
}

#navText {
  color: white;
}

/* Color behind content should be lightgrey */
#backgroundColorLightGrey {
  background-color: lightgrey;
}

/* Styling for container that holds all content for page */
#contentArea {
  background-color: white;
  margin-top: 50px;
  padding-top: 10px;
  padding-bottom: 10px;
  padding-left: 20px;
  padding-right: 20px;
}

/* Initial note about the purpose of the page */
#InitialMessage {
  padding-bottom: 30px;
}

/* Leaf Icon for Paperless Delivery */
#leaf {
  width: 50px;
}
/* Inner message about paperless delivery */
#deliveryPreferences {
  padding-top: 10px;
  padding-bottom: 2px;
  background-color: #e8ecf7;
}

/* All content and filters related to searching for documents */
#DocumentSearching {
  height: 100px;
  padding-left:1vw;
}

/* Searchbar styling */
#SearchBar {
  padding-top: 10px;
  padding-bottom: 10px;
  margin-top: 15px;
}
#SearchLogo {

  margin-top: 15px;
  border: 1px solid;
  border-color: lightgrey;
}
#SearchForDocumentText {
  margin-left:1vw;
}

/* Styling for all button dropdowns */
#buttonDropdown {
  border: 2px solid;
  margin-left: 0px;
  padding-top: 0px;
  border-radius: 4px;
  border-color: lightgrey;
  font-size: 1.8vmin;
  width: 100%;
  height: 100%;
  text-align: left;
  color:rgb(50, 50, 50);
}

#selectFilterMessage {
  text-align: left;
  margin-left: .5vmax;
  margin-bottom: 1vw;
}

#dropdown-item-filter {
  margin-right: 125px;
}

#filterCurrent {
  margin-left:.5vmax;
}

#filterCurrent-Plan {
  margin-left:.5vmax;
}

#filterCurrent-Type {
  margin-left:.5vmax;
}

#filterCurrent-Members {
  margin-left:.5vmax;
}

#filterCol {
  padding-right: .1vw;
}

#dropdown-item-plan {
  margin-right: 310px;
}

#filters {
  margin-bottom: 1vw;
  padding-left:1vw;
}

#dropdown-item-dependent {
  margin-right: 30px;
}

#dropdown-item-date {
  margin-right: 75px;
}

#dropdowns {
  width:15vw;
  padding-right:0px;
}

#PlanDropdown {
  width:23vw;
  padding-right:0px;
}

#TextLink {
  color: #3e79c1;
  text-align: center;
  font-size: large;
}

#ClearFilterMessage {
  color: #3e79c1;
  text-align: center;
  font-size: medium;
}

.DownloadImg {
  width: 12px;
  height: 12px;
}

#Text-PaperlessDelivery {
  font-size: 20px;
}

#documentTable {
  border: 1px solid;
  border-color: lightgrey;
}

#startDate {
  height: 55px;
}

.clearButton {
  border: 0;
  text-align: center;
  background-color: white;
}

.clearButton:hover {
  background-color: rgb(246, 245, 249);
}

th {
  color: rgb(179, 178, 178);
}

#docName {
  color: #3e79c1;
}

#DateToggleSorting {
  border-width:0px;
  border-radius:5px;
  color:grey;
  background-color:rgb(255, 255, 255);
  margin-left:5px;
}

#EntireTable {
  margin-left:1vw;
}

#typePillsEOB {
  background-color: black;
  border-radius: 30px;
  height: 30px;
  color: white;
  padding-top: 0px;
}

#typePillsEnrollment {
  background-color: #dfb642;
  border-radius: 30px;
  height: 30px;
  color: white;
  padding-top: 0px;
}


#typePillsLetter {
  background-color: rgb(97, 95, 95);
  border-radius: 30px;
  height: 30px;
  color: white;
  padding-top: 0px;
}

#typePillsIDCard {
  background-color: #2f688d;
  border-radius: 30px;
  height: 30px;
  color: white;
  padding-top: 0px;
}

#typePillsCoverage {
  background-color: #133258;
  border-radius: 30px;
  height: 30px;
  color: white;
  padding-top: 0px;
}

#magGlass {
  font-size: 25px;
}

#typePillsBill {
  background-color: green;
  border-radius: 30px;
  height: 30px;
  color: white;
  padding-top: 0px;
}

#typePillsUser {
  background-color: darkblue;
  border-radius: 50%;
  color: white;
  width: 2.5em;
  height: 2.5em;
  font-size: 1.25em;
  padding: 0;
}

#DocumentsPagination {
  margin-top: 20px;
  margin-right: 170px;
  display: flex;
  justify-content: right;
  align-items: center;
}

#Message-PaginationMessage {
  font-size:.8vmax;
  padding-right:10px;
}

#PaginationPageChangePrevious {
  border-color: lightgrey;
  border-width: 1px;
  margin-right:7px;
}

#PaginationPageChangeNext {
  border-color: lightgrey;
  border-width: 1px;
}

#PaginationPageChangePrevious:hover {
  box-shadow: 0 2px 4px 0 rgba(0,0,0,0.16), 0 4px 8px 0 rgba(0,0,0,0.17);
}

#PaginationPageChangeNext:hover {
  box-shadow: 0 2px 4px 0 rgba(0,0,0,0.16), 0 4px 8px 0 rgba(0,0,0,0.17);
}

.DocumentSearchLogo {
  width: 20px;
  height: 20px;
  margin-right: 20px;
}
</style>
