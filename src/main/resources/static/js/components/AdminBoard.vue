<template>
  <v-container>
    <h1>Admin Board</h1>
    <v-row>

      <v-col cols="12">
        <pie-chart></pie-chart>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import PieChart from "./BarChart";

export default {
  name: "AdminBoard",
  components: {
    PieChart
  }

}
</script>

<style scoped>

</style>