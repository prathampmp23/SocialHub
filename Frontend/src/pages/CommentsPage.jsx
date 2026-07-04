import React, { useState, useEffect } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import {
  Container,
  Typography,
  Box,
  CircularProgress,
  Paper,
  List,
  ListItem,
  ListItemAvatar,
  ListItemText,
  Divider,
  TextField,
  Button,
  Avatar,
  Alert,
  Card,
  CardContent,
  Collapse,
  IconButton,
  Chip,
} from '@mui/material';
import { useAuth } from '../context/AuthContext';
import { postsService, commentsService } from '../services/api';
import SendIcon from '@mui/icons-material/Send';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import ExpandLessIcon from '@mui/icons-material/ExpandLess';
import ReplyIcon from '@mui/icons-material/Reply';
import ThumbUpIcon from '@mui/icons-material/ThumbUp';

const CommentsPage = () => {
  const { postId } = useParams();
  const navigate = useNavigate();
  const { user, token } = useAuth();
  const [post, setPost] = useState(null);
  const [comments, setComments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [newComment, setNewComment] = useState('');
  const [submitting, setSubmitting] = useState(false);
  const [expandedThreads, setExpandedThreads] = useState({});
  const [replyingTo, setReplyingTo] = useState(null);

  useEffect(() => {
    fetchPostAndComments();
  }, [postId]);

  const fetchPostAndComments = async () => {
    try {
      setLoading(true);
      // Fetch post comments (which includes comments for the post)
      const commentsResponse = await postsService.getPostComments(postId);
      setComments(commentsResponse.data || []);
      // Mock post data - in real scenario you'd fetch post separately
      setPost({
        postid: postId,
        title: `Post #${postId}`,
        description: 'A great discussion post',
        author: 'Author Name',
      });
      setError(null);
    } catch (err) {
      console.error('Error fetching comments:', err);
      setError('Failed to load comments. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  const handleAddComment = async () => {
    if (!token) {
      navigate('/login');
      return;
    }

    if (!newComment.trim()) {
      setError('Comment cannot be empty');
      return;
    }

    try {
      setSubmitting(true);
      const commentData = {
        comment: newComment,
        commenter: user || 'Anonymous',
        postid: parseInt(postId),
        parentCommentId: replyingTo?.commentid || replyingTo?.id || null,
      };
      const response = await commentsService.createComment(commentData);
      const newCommentObj = {
        ...response.data,
        replies: [],
        likedBy: [],
      };

      if (replyingTo) {
        // Add reply to existing comment
        const updateComments = (commentList) => {
          return commentList.map((c) => {
            if ((c.commentid || c.id) === (replyingTo.commentid || replyingTo.id)) {
              return { ...c, replies: [...(c.replies || []), newCommentObj] };
            }
            if (c.replies && c.replies.length > 0) {
              return { ...c, replies: updateComments(c.replies) };
            }
            return c;
          });
        };
        setComments(updateComments(comments));
      } else {
        // Add as main comment
        setComments([...comments, newCommentObj]);
      }

      setNewComment('');
      setReplyingTo(null);
      setError(null);
    } catch (err) {
      console.error('Error adding comment:', err);
      setError('Failed to add comment. Please try again.');
    } finally {
      setSubmitting(false);
    }
  };

  const toggleThread = (commentId) => {
    setExpandedThreads((prev) => ({
      ...prev,
      [commentId]: !prev[commentId],
    }));
  };

  // Component to render nested comments with threading
  const CommentThread = ({ comment, level = 0 }) => {
    const commentId = comment.commentid || comment.id;
    const replies = comment.replies || [];
    const isExpanded = expandedThreads[commentId] !== false; // Default to expanded
    const borderColor = ['#6366F1', '#EC4899', '#10B981', '#F59E0B'][level % 4];

    return (
      <Box key={commentId}>
        <Box
          sx={{
            display: 'flex',
            ml: level * 3,
            mb: 2,
            borderLeft: `3px solid ${borderColor}`,
            pl: 2,
            transition: 'all 0.2s ease',
          }}
        >
          <Box sx={{ flex: 1 }}>
            <Box sx={{ display: 'flex', alignItems: 'center', mb: 1 }}>
              <Avatar
                sx={{
                  bgcolor: ['#EC4899', '#6366F1', '#10B981', '#F59E0B'][level % 4],
                  width: 32,
                  height: 32,
                  mr: 1,
                  fontSize: '0.9rem',
                }}
              >
                {comment.commenter ? comment.commenter.charAt(0).toUpperCase() : 'U'}
              </Avatar>
              <Box>
                <Typography variant="subtitle2" sx={{ fontWeight: 700 }}>
                  {comment.commenter}
                </Typography>
                <Typography variant="caption" color="textSecondary" sx={{ ml: 0.5 }}>
                  {level > 0 ? 'Reply • ' : ''}{new Date().toLocaleDateString()}
                </Typography>
              </Box>
            </Box>

            <Typography variant="body2" sx={{ mb: 1, lineHeight: 1.6 }}>
              {comment.comment}
            </Typography>

            <Box sx={{ display: 'flex', gap: 1, alignItems: 'center', mt: 1 }}>
              <Button
                size="small"
                startIcon={<ReplyIcon sx={{ fontSize: '1rem' }} />}
                onClick={() => setReplyingTo(comment)}
                sx={{
                  color: borderColor,
                  fontWeight: 600,
                  fontSize: '0.75rem',
                  textTransform: 'uppercase',
                  '&:hover': { bgcolor: `${borderColor}15` },
                }}
              >
                Reply
              </Button>

              {(comment.likedBy?.length || 0) > 0 && (
                <Chip
                  icon={<ThumbUpIcon sx={{ fontSize: '0.9rem' }} />}
                  label={`${comment.likedBy.length}`}
                  size="small"
                  variant="outlined"
                  sx={{ height: 24, fontSize: '0.75rem' }}
                />
              )}

              {replies.length > 0 && (
                <Button
                  size="small"
                  onClick={() => toggleThread(commentId)}
                  endIcon={isExpanded ? <ExpandLessIcon /> : <ExpandMoreIcon />}
                  sx={{
                    ml: 'auto',
                    color: borderColor,
                    fontWeight: 600,
                    fontSize: '0.75rem',
                  }}
                >
                  {replies.length} {replies.length === 1 ? 'reply' : 'replies'}
                </Button>
              )}
            </Box>
          </Box>
        </Box>

        {/* Nested Replies */}
        <Collapse in={isExpanded} timeout="auto" unmountOnExit>
          {replies.map((reply) => (
            <CommentThread key={reply.commentid || reply.id} comment={reply} level={level + 1} />
          ))}
        </Collapse>
      </Box>
    );
  };

  if (loading) {
    return (
      <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '60vh' }}>
        <CircularProgress size={60} />
      </Box>
    );
  }

  return (
    <Container maxWidth="md">
      <Button
        startIcon={<ArrowBackIcon />}
        onClick={() => navigate('/posts')}
        sx={{ mb: 3, color: '#5E35B1', fontWeight: 600 }}
      >
        Back to Posts
      </Button>

      {error && (
        <Alert severity="error" sx={{ mb: 3 }} onClose={() => setError(null)}>
          {error}
        </Alert>
      )}

      {post && (
        <Card sx={{ mb: 4, boxShadow: '0 4px 12px rgba(0, 0, 0, 0.08)' }}>
          <CardContent>
            <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
              <Avatar sx={{ bgcolor: '#FF6B6B', mr: 2 }}>
                {post.author ? post.author.charAt(0).toUpperCase() : 'A'}
              </Avatar>
              <Box>
                <Typography variant="subtitle2" sx={{ fontWeight: 700 }}>
                  {post.author}
                </Typography>
                <Typography variant="caption" color="textSecondary">
                  {new Date().toLocaleDateString()}
                </Typography>
              </Box>
            </Box>
            <Typography variant="h5" sx={{ fontWeight: 700, mb: 2 }}>
              {post.title}
            </Typography>
            <Typography variant="body1" color="textSecondary">
              {post.description}
            </Typography>
          </CardContent>
        </Card>
      )}

      <Box sx={{ mb: 4 }}>
        <Box sx={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', mb: 3 }}>
          <Typography variant="h6" sx={{ fontWeight: 700 }}>
            💬 Conversation
          </Typography>
          <Chip
            label={`${comments.reduce((acc, c) => acc + 1 + (c.replies?.length || 0), 0)} messages`}
            variant="outlined"
            color="primary"
          />
        </Box>

        {comments.length === 0 ? (
          <Paper sx={{ p: 3, textAlign: 'center', mb: 4, bgcolor: '#F5F5F5' }}>
            <Typography color="textSecondary">
              🤐 No comments yet. Be the first to start the conversation!
            </Typography>
          </Paper>
        ) : (
          <Paper sx={{ p: 3, bgcolor: '#FAFAFA', borderRadius: 2 }}>
            {comments.map((comment) => (
              <CommentThread key={comment.commentid || comment.id} comment={comment} />
            ))}
          </Paper>
        )}
      </Box>

      {replyingTo && (
        <Alert severity="info" sx={{ mb: 3 }} onClose={() => setReplyingTo(null)}>
          <Typography variant="body2" sx={{ fontWeight: 600 }}>
            Replying to <strong>{replyingTo.commenter}</strong>
          </Typography>
          <Typography variant="caption">{replyingTo.comment}</Typography>
        </Alert>
      )}

      {token ? (
        <Paper sx={{ p: 3, borderRadius: 2, background: 'linear-gradient(135deg, #FAFAFA 0%, #F5F5F5 100%)' }}>
          <Typography variant="h6" sx={{ fontWeight: 700, mb: 2, display: 'flex', alignItems: 'center', gap: 1 }}>
            ✍️ Add {replyingTo ? 'a reply' : 'a comment'}
          </Typography>
          <TextField
            fullWidth
            multiline
            rows={3}
            variant="outlined"
            placeholder={replyingTo ? `Reply to ${replyingTo.commenter}...` : 'Share your thoughts...'}
            value={newComment}
            onChange={(e) => setNewComment(e.target.value)}
            sx={{
              mb: 2,
              '& .MuiOutlinedInput-root': {
                borderRadius: 2,
                '&:hover fieldset': {
                  borderColor: '#5E35B1',
                },
              },
            }}
            disabled={submitting}
          />
          <Box sx={{ display: 'flex', gap: 1 }}>
            <Button
              variant="contained"
              endIcon={<SendIcon />}
              onClick={handleAddComment}
              disabled={submitting}
              sx={{
                background: 'linear-gradient(135deg, #5E35B1 0%, #7E57C2 100%)',
                fontWeight: 700,
              }}
            >
              {submitting ? 'Posting...' : 'Post Comment'}
            </Button>
            {replyingTo && (
              <Button
                variant="outlined"
                onClick={() => setReplyingTo(null)}
                sx={{ fontWeight: 600 }}
              >
                Cancel
              </Button>
            )}
          </Box>
        </Paper>
      ) : (
        <Alert severity="info">
          <Link to="/login" style={{ textDecoration: 'none', fontWeight: 600 }}>
            Sign in
          </Link>
          {' '}to add comments.
        </Alert>
      )}
    </Container>
  );
};

export default CommentsPage;
