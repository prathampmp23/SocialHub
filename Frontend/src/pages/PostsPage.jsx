import React, { useState, useEffect } from 'react';
import {
  Container,
  Typography,
  Grid,
  Card,
  CardContent,
  CardActions,
  Button,
  CircularProgress,
  Box,
  TextField,
  Paper,
  Alert,
  Avatar,
  Chip,
} from '@mui/material';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { postsService } from '../services/api';
import CreateIcon from '@mui/icons-material/Create';
import CommentIcon from '@mui/icons-material/Comment';

const PostsPage = () => {
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [newPostTitle, setNewPostTitle] = useState('');
  const [newPostContent, setNewPostContent] = useState('');
  const [creating, setCreating] = useState(false);
  const { user, token } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    fetchPosts();
  }, []);

  const fetchPosts = async () => {
    try {
      setLoading(true);
      const response = await postsService.getAllPosts();
      setPosts(response.data || []);
      setError(null);
    } catch (err) {
      console.error('Error fetching posts:', err);
      setError('Failed to load posts. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  const handleCreatePost = async () => {
    if (!token) {
      navigate('/login');
      return;
    }

    if (!newPostTitle.trim() || !newPostContent.trim()) {
      setError('Please fill in both title and content');
      return;
    }

    try {
      setCreating(true);
      const newPost = {
        title: newPostTitle,
        description: newPostContent,
        author: user || 'Anonymous',
      };
      const response = await postsService.createPost(newPost);
      setPosts([response.data, ...posts]);
      setNewPostTitle('');
      setNewPostContent('');
      setError(null);
    } catch (err) {
      console.error('Error creating post:', err);
      setError('Failed to create post. Please try again.');
    } finally {
      setCreating(false);
    }
  };

  if (loading) {
    return (
      <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '60vh' }}>
        <CircularProgress size={60} />
      </Box>
    );
  }

  return (
    <Container maxWidth="lg">
      <Box sx={{ mb: 6 }}>
        <Typography
          variant="h3"
          component="h1"
          gutterBottom
          sx={{
            fontWeight: 800,
            mb: 1,
            background: 'linear-gradient(180deg, #009a97 50%, #000000 100%)',
            backgroundClip: 'text',
            WebkitBackgroundClip: 'text',
            WebkitTextFillColor: 'transparent',
          }}
        >
          Posts
        </Typography>
        <Typography variant="body1" color="textSecondary" sx={{ opacity: 0.7 }}>
          Share your thoughts and engage with the community
        </Typography>
      </Box>

      {error && (
        <Alert severity="error" sx={{ mb: 4 }} onClose={() => setError(null)}>
          {error}
        </Alert>
      )}

      {token && (
        <Paper
          elevation={0}
          sx={{
            p: 4,
            mb: 6,
            borderRadius: 2,
            backdropFilter: 'blur(20px)',
            background: 'linear-gradient(135deg, rgba(255, 255, 255, 0.25) 0%, rgba(255, 255, 255, 0.15) 100%)',
            border: '1px solid rgba(255, 255, 255, 0.18)',
            boxShadow: '0 8px 32px rgba(0, 0, 0, 0.08)',
          }}
        >
          <Typography variant="h6" sx={{ fontWeight: 700, mb: 3, color: '#008f86' }}>
            Create a new post
          </Typography>
          <TextField
            fullWidth
            label="Post Title"
            variant="outlined"
            value={newPostTitle}
            onChange={(e) => setNewPostTitle(e.target.value)}
            sx={{ mb: 2 }}
            disabled={creating}
          />
          <TextField
            fullWidth
            multiline
            rows={4}
            variant="outlined"
            label="What's on your mind?"
            value={newPostContent}
            onChange={(e) => setNewPostContent(e.target.value)}
            sx={{ mb: 3 }}
            disabled={creating}
          />
          <Button
            variant="contained"
            onClick={handleCreatePost}
            disabled={creating}
            startIcon={<CreateIcon />}
            sx={{
              background: 'linear-gradient(135deg, #00ffee 0%, #000b85) 100%)',
              fontWeight: 700,
              backdropFilter: 'blur(10px)',
              border: '1px solid rgba(255, 255, 255, 0.18)',
            }}
          >
            {creating ? 'Publishing...' : 'Publish Post'}
          </Button>
        </Paper>
      )}

      {!token && (
        <Alert severity="info" sx={{ mb: 4 }}>
          <Link to="/login" style={{ textDecoration: 'none', fontWeight: 600 }}>
            Sign in
          </Link>
          {' '}to create posts and engage with the community.
        </Alert>
      )}

      {posts.length === 0 ? (
        <Box
          sx={{
            textAlign: 'center',
            py: 8,
            backdropFilter: 'blur(10px)',
            background: 'rgba(255, 255, 255, 0.1)',
            borderRadius: 3,
            border: '1px solid rgba(255, 255, 255, 0.18)',
          }}
        >
          <CreateIcon sx={{ fontSize: 60, color: 'rgba(99, 102, 241, 0.3)', mb: 2 }} />
          <Typography variant="h6" color="textSecondary" sx={{ opacity: 0.7 }}>
            No posts yet. Be the first to share!
          </Typography>
        </Box>
      ) : (
        <Grid container spacing={3}>
          {posts.map((post) => (
            <Grid item key={post.postid || post.id} xs={12} sm={6} md={4}>
              <Card
                sx={{
                  height: '100%',
                  display: 'flex',
                  flexDirection: 'column',
                  transition: 'all 0.3s cubic-bezier(0.4, 0, 0.2, 1)',
                  '&:hover': {
                    transform: 'translateY(-8px)',
                    boxShadow: '0 16px 48px rgba(99, 102, 241, 0.15)',
                  },
                }}
              >
                <CardContent sx={{ flexGrow: 1 }}>
                  <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
                    <Avatar
                      sx={{
                        background: 'linear-gradient(135deg, #00ffee 0%, #000b85 100%)',
                        mr: 2,
                        boxShadow: '0 4px 12px rgba(99, 102, 241, 0.2)',
                      }}
                    >
                      {post.author ? post.author.charAt(0).toUpperCase() : 'A'}
                    </Avatar>
                    <Box>
                      <Typography variant="caption" sx={{ fontWeight: 700, color: '#008f86' }}>
                        {post.author || 'Anonymous'}
                      </Typography>
                      <Typography variant="caption" color="textSecondary" display="block" sx={{ opacity: 0.7 }}>
                        {new Date().toLocaleDateString()}
                      </Typography>
                    </Box>
                  </Box>
                  <Typography
                    variant="h6"
                    component="div"
                    gutterBottom
                    sx={{ fontWeight: 700, mb: 1 }}
                  >
                    {post.title}
                  </Typography>
                  <Typography variant="body2" color="textSecondary" sx={{ mb: 2, opacity: 0.7 }}>
                    {post.description}
                  </Typography>
                </CardContent>
                <CardActions sx={{ pt: 0 }}>
                  <Button
                    size="small"
                    component={Link}
                    to={`/comments/${post.postid || post.id}`}
                    startIcon={<CommentIcon />}
                    sx={{
                      color: '#008f86',
                      fontWeight: 600,
                    }}
                  >
                    Comments
                  </Button>
                </CardActions>
              </Card>
            </Grid>
          ))}
        </Grid>
      )}
    </Container>
  );
};

export default PostsPage;
