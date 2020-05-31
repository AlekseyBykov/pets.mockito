package alekseybykov.portfolio.mockito.dependencies.external;

import java.util.List;

/**
 * Some external dependency without implementation.
 *
 * @author Aleksey Bykov
 * @since 15.03.2020
 */
public interface DocumentService {

	public List<String> searchDocumentsByName(String documentName);

	public void deleteDocumentByName(String documentName);

	public void deleteDocuments(List<String> documentNames);
}
