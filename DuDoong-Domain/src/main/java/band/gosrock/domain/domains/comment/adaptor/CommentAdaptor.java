package band.gosrock.domain.domains.comment.adaptor;


import band.gosrock.common.annotation.Adaptor;
import band.gosrock.domain.domains.comment.domain.Comment;
import band.gosrock.domain.domains.comment.dto.condition.CommentCondition;
import band.gosrock.domain.domains.comment.exception.CommentNotFoundException;
import band.gosrock.domain.domains.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

@Adaptor
@RequiredArgsConstructor
public class CommentAdaptor {

    private final CommentRepository commentRepository;

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public Slice<Comment> searchComment(CommentCondition commentCondition) {
        PageRequest pageRequest = PageRequest.of(0, 20, Sort.by("createdAt").ascending());
        return commentRepository.searchToPage(commentCondition, pageRequest);
    }

    public Comment queryComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> CommentNotFoundException.EXCEPTION);
    }
}
