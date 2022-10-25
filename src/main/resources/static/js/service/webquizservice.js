import axios from "axios";

class WebQuizService {

    getTest(id) {
        return axios.get('http://localhost:8889/api/quizzes/' + id, {
            headers: {"Access-Control-Allow-Origin": "*"}, 'Access-Control-Allow-Credentials': true


        });
    }

    completeTest(id, answers) {
        return axios.post('http://localhost:8889/api/quizzes/' + id + '/solve', {
            answer: answers
        })
    }

    async getQuizzesIn30Days() {
        return axios.get('http://localhost:8889/api/quizzes/chart');
    }

    createTest(title, text, options, answers) {
        return axios.post('http://localhost:8889/api/quizzes', {
            title: title,
            text: text,
            options: options,
            answer: answers
        })
    }

    getAllQuizzes(page, size) {
        if (page == null || size == null) {
            return axios.get('http://localhost:8889/api/quizzes');
        }
        return axios.get('http://localhost:8889/api/quizzes?page='+page+'&size='+size);
    }

}

export default new WebQuizService();

