<template>
  <v-container>
    <v-row>
      <v-col cols="5" md="3" lg="3" xl="3" sm="3" xs="3">
        <v-img :src="currentUser.userpic"></v-img>
      </v-col>
      <v-col cols="6">
        <p>Name:
          <b>{{ currentUser.name }}</b>
        </p>
        <p> Email:
          <b>{{ currentUser.email }}</b>
        </p>
        <p v-if="currentUser.gender"> Gender:
          <b>{{ currentUser.gender }}</b>
        </p>
        <p> Role:
          <b>{{ currentUser.role }}</b>
        </p>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "ProfileView",
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
  },
  mounted() {
    console.log(this.currentUser);
  }
}
</script>

<style scoped>

</style>