<template>
  <v-container>
    <h1>All quizzes</h1>
    <v-col cols="6" align="center" v-if="loading">
      <v-progress-circular class="transition-ease-in-out" indeterminate size="50" width="3">
      </v-progress-circular>
    </v-col>
    <div v-else>
      <h3>Page {{ this.quizzes.number + 1 }}/{{ this.quizzes.totalPages }}</h3>
      <v-row class="mt-2">
        <v-pagination class="text-left"
                      v-model="page"
                      :length="quizzes.totalPages"
        ></v-pagination>
      </v-row>
      <v-row>

        <v-col cols="12" xs="12" sm="6" md="4" lg="3" xl="2" :key="key" v-for="(quiz,key) in quizzes.content">
          <v-card>
            <v-card-title> {{ quiz.title }}</v-card-title>

            <v-card-text>
              {{ quiz.text }}
            </v-card-text>

            <v-card-actions>
              <v-btn :to="'/get/'+quiz.id">GET</v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </div>
  </v-container>
</template>

<script>
import Webquizservice from "../service/webquizservice";

export default {
  name: "GetAllView",
  data() {
    return {
      page: 1,
      quizzes: null,
      loading: true,
    }
  },
  created() {
    this.getAllQuizzes();

  },
  watch: {
    page(newPage) {
      this.getAllQuizzes(newPage);

    }
  },
  methods: {
    getAllQuizzes(page) {
      Webquizservice.getAllQuizzes(page == null ? this.page - 1 : page - 1, 10).then((response) => {
        this.quizzes = response.data;
        this.page = this.quizzes.number + 1;
        console.log(response.data)
        this.loading = false;
      })
    },
  }
}
</script>

<style scoped>

</style>