<template>
  <!-- App.vue -->
  <v-app>
    <v-app-bar app>
      <v-btn to="/create" v-if="currentUser">CREATE</v-btn>
      <v-spacer></v-spacer>
      <v-btn to="/">GET ALL</v-btn>


      <v-spacer></v-spacer>
      <v-btn to="/get">GET BY ID</v-btn>
      <v-spacer></v-spacer>
      <div class="text-center">
        <v-menu v-if="currentUser"
                v-model="menu"
                :close-on-content-click="false"
                :nudge-width="200">
          <template v-slot:activator="{ on, attrs }">
            <v-btn v-bind="attrs" v-on="on">
              {{ currentUser.name }}
            </v-btn>
          </template>
          <v-list>
            <router-link to="/profile" style="text-decoration: none">
              <v-list-item link>
                <v-list-item-title>PROFILE</v-list-item-title>
              </v-list-item>
            </router-link>
            <router-link style="text-decoration: none" v-if="currentUser.role && currentUser.role==='ADMIN'"
                         to="/admin">
              <v-list-item link>
                <v-list-item-title style="color: #2196F3">ADMIN</v-list-item-title>
              </v-list-item>
            </router-link>
            <v-list-item link href="/logout">
              <v-list-item-title style="color: red">LOGOUT</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>
        <div v-else>
          <v-btn href="/login" color="primary">LOGIN</v-btn>
        </div>
      </div>

    </v-app-bar>
    <!-- Sizes your content based upon application components -->
    <v-main>
      <!-- Provides the application the proper gutter -->
      <v-container fluid>
        <!-- If using vue-router -->
        <router-view></router-view>
      </v-container>
    </v-main>
    <v-footer padless>
      <v-col class="text-center" cols="12">
        {{ new Date().getFullYear() }} — <strong>Elisey Osadchy</strong>
      </v-col>
    </v-footer>
  </v-app>
</template>

<script>
export default {
  data() {
    return {
      menu: false,
    }
  },
  computed: {

    currentUser() {
      return this.$store.state.auth.user;
    },
  }
}
</script>

