package alekseybykov.portfolio.mockito.dependencies.external;

import java.util.List;

// Some external dependency without implementation.
public interface DocumentService {

	public List<String> searchDocuments(String documentName);

	void deleteDocument(String documentName);
}
