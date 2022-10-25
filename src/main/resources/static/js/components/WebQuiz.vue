<template>
  <v-container>
    <div>
      <v-text-field label="Enter ID of quiz:" v-model="id"></v-text-field>
      <v-btn :to="'/get/'+id">Get</v-btn>
    </div>

  </v-container>
</template>

<script>
export default {
  name: "WebQuiz",
  data() {
    return {
      id: 0,

    };
  },

}
</script>

<style scoped>

</style>