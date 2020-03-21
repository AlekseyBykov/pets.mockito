package alekseybykov.portfolio.mockito.dependencies.external;

import java.util.List;

// Some external dependency without implementation.
public interface DocumentService {

	public List<String> searchDocumentsByName(String documentName);

	public void deleteDocumentByName(String documentName);

	public void deleteDocuments(List<String> documentNames);
}
