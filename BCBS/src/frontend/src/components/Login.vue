<template>
    <div class="container ">

   

<div class="login-box d-flex flex-column ">

    <h1 class="m-2">LOG IN</h1>

      <div id="errorbox" class="ferror" v-if="error">{{error}}</div>
      <form @submit.prevent ="handleSubmit">
        <div class="row align-items-center justify-content-center m-2">
          <div class="col-md-6 col-9">
            <div class="form-floating">
              <input type="text" class="form-control" placeholder="Username" id="username" required v-model="username">
              <label for="text" >Username</label>
            </div>
          </div>
        </div>

        <div class="row align-items-center justify-content-center m-2">
          <div class="col-md-6 col-9">
            <div class="form-floating">
              <input type="password" class="form-control" placeholder="Password" id="password" required v-model="password">
              <label for="password" >Password</label>
            </div>
          </div>
        </div>
        
       
        <div class="row align-items-center justify-content-center m-2 submit">
            <button id="loginButton" class="btn btn-success m-2 btn-lg row align-items-center justify-content-center"  value="Log In">Log In</button>

        </div>

      </form>
</div>
    </div>
</template>

<script> 
export default {
  data() {
    return {
      username: '',
      password: '',
      users: ['Jacob', "Joseph", "Zander", "Pagliacci"],
      error: ''
    }
  },
  methods: {
    
    handleSubmit() {
      this.error = ''
      if(!this.users.some((user) => user == this.username)) {
        this.error = "This user does not exist"
        
      }
      else if (this.password != 'password') {
        this.error = "Incorrect password"
      }

      else {
        this.$emit("loggedin", this.username)
      }
    }
  }
}
</script>


<style scoped>

h1 {
  margin: 20px auto;
  font-family: 'Ubuntu', sans-serif;
  color: #00AEEF;
  text-align: center;
  letter-spacing: 5px;
 
}


.login-box {
    border-width: 1rem;
    box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px;
    height: 100;
    width: 90%;
    max-width: 700px;
    margin: auto;

    border-radius: 6px;
    background-color: white;
}

#loginButton {
  max-width: 200px;
  height: 50px;
}

.ferror {
  text-align: center;
  color: red;
  font-weight: bolder;
}

</style>

