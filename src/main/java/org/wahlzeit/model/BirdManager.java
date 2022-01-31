package org.wahlzeit.model;

import org.wahlzeit.services.ObjectManager;
import org.wahlzeit.services.Persistent;
import org.wahlzeit.utils.PatternInstance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@PatternInstance(
        patternName = "Singleton", participants = {}
)
/*
---- cw12 ----
Manager collaboration:
BirdManager (Manager) -> Bird (Element)
The manager object (BirdManager) binds to the element (Bird) class.

The Collaboration is responsible for the creation of bridge objects.
 */
public class BirdManager extends ObjectManager {

    private static final BirdManager instance = new BirdManager();

    private final Map<String, BirdType> birdTypeMap = new ConcurrentHashMap<>();

    public BirdManager() {}

    public static synchronized BirdManager getInstance() {
        return instance;
    }

    @Override
    protected Bird createObject(ResultSet rset) throws SQLException {
        return new Bird(rset);
    }

    public BirdType getOrCreateBirdType(String typeName) {
        return birdTypeMap.computeIfAbsent(typeName, BirdType::new);
    }

    public Bird getOrCreateBird(String species, BirdType birdType) {
        return new Bird(species, getOrCreateBirdType(birdType.getName()));
    }

    public Bird getOrCreateBird(String species, String birdTypeName) {
        return new Bird(species, getOrCreateBirdType(birdTypeName));
    }

    public Map<String, BirdType> getBirdTypeMap() {
        return birdTypeMap;
    }
}
