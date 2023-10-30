<script setup>
import TopDash from './components/TopDash.vue'
import Login from './components/Login.vue'
import Embedded from './components/EmbeddedApplicationTest.vue'
</script>

<template>

  <!-- Display Login Page -->
  <div v-if="!LoggedIn">
    <div id="outPopUp"><Login @loggedin="LogIn" /></div>
  </div>
  <!-- Display Mock Page -->
  <div v-if="LoggedIn">
    <Embedded :bcbsId="bcbsId"/>
  </div>
  
</template>

<script>
export default {
  name: 'App',
  // Defaulting to login page
  data()  {
     return {
      user : '',
      LoggedIn: false,
      bcbsId: ''
     }
  },
  // Setting components that will be used
  components: {
    TopDash,
    Login,
    Embedded
  },
  // Logs user in if user is valid user
  methods : {
    LogIn(user) {
      this.user = user
      this.bcbsId = this.getId(user)
      this.LoggedIn = true
      
    },
  
  getId(user) {
    switch(user) {
      case "Jacob":
        return "INTJ123456789"
      case "Zander":
        return "ISTP112233445"
      case "Joseph":
        return "ESTP987654321"
      case "Pagliacci":
        return "ISFJ998877665"
    }

  }
}
}
</script>

<style>


html, body {
  margin:0;
  padding:0;
  height:100%;
}
#outPopUp {
  position: absolute;

  width : 700px;
  height: 400px;
  top: 50%;
  left: 50%;
  margin: -100px 0 0 -350px;
  
}

</style>