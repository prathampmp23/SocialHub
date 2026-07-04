import axios from "axios";

const API_BASE_URL = "http://localhost:8765";
const POSTS_API = `${API_BASE_URL}/api/posts`;
const COMMENTS_API = `${API_BASE_URL}/api/comments`;
const AUTH_API = `${API_BASE_URL}/auth`;

const api = axios.create({
  baseURL: API_BASE_URL,
});

// Add token to headers if it exists
api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  // console.log("TOKEN:", token);
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  // console.log(config.headers);
  return config;
});

// Authentication APIs
export const authService = {
  login: (username, password) => {
    return api.post("/auth/login", { username, password });
  },
  register: (username, password, role) => {
    return api.post("/auth/register", { username, password, role });
  },
};

// Posts APIs
export const postsService = {
  getAllPosts: () => {
    return api.get("/api/posts");
  },
  createPost: (post) => {
    return api.post("/api/posts", post);
  },
  getPostComments: (postId) => {
    return api.get(`/api/posts/${postId}`);
  },
};

// Comments APIs
export const commentsService = {
  getAllComments: () => {
    return api.get("/api/comments");
  },
  createComment: (comment) => {
    return api.post("/api/comments", comment);
  },
  getCommentsByPostId: (postId) => {
    return api.get(`/api/comments/${postId}`);
  },
};

export default api;
