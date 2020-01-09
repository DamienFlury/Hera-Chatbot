package hera.store.unit;

import hera.database.entities.mapped.Token;
import hera.database.entities.persistence.TokenPO;
import hera.database.types.TokenKey;
import hera.store.exception.FailedAfterRetriesException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class TokenAccessUnit extends StorageAccessUnit<TokenPO, Token>{

	public TokenAccessUnit() {
		super(TokenPO.ENTITY_NAME);
	}
	private static final Logger LOG = LoggerFactory.getLogger(GuildSettingAccessUnit.class);

	public List<Token> forKey(TokenKey key) {
		return data.stream().filter((t) -> t.getKey().equals(key)).collect(Collectors.toList());
	}

	public void update(Token token) {
		try {
			List<Token> foundTokens = forKey(token.getKey());

			foundTokens.get(0).setToken(token.getToken());
			retryOnFail(() -> dao.update(TokenPO.class, foundTokens.get(0), foundTokens.get(0).getId()));

		} catch(FailedAfterRetriesException e) {
			LOG.error("Error while trying to add entity of type GuildSettingsPO");
			LOG.debug("Stacktrace:", e);
		}
	}
}
