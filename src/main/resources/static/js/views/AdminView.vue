<template>
  <admin-board v-if="currentUser.role==='ADMIN'"></admin-board>
  <v-container align="center" v-else><h1><v-icon>mdi-cancel</v-icon>Ви не маєте доступу до цієї сторінки.</h1></v-container>
</template>

<script>
import AdminBoard from "../components/AdminBoard";

export default {
  name: "AdminView",
  components: {
    AdminBoard
  },
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
  }
}
</script>

<style scoped>

</style>