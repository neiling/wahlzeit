package org.wahlzeit.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BirdType {

    private BirdType superType;
    private final Set<BirdType> subTypes = new HashSet<>();

    // The basic idea here is to be able to represent the taxonomy of the bird by the BirdType.
    /* Example: Great tit (Parus major), source
             Kingdom: Animalia
                //
           Phylum: Chordata
              //
         Class: Aves
            //
        Order: Passeriformes
          //
      Family: Paridae
        //
    Species: P. major
    */
    private final String name;

    public BirdType(final String name) {
        assertNotNull("name", name);
        this.name = name;
    }

    public void addSubType(final BirdType birdType) {
        assertNotNull("birdType", birdType);
        birdType.setSuperType(this);
        subTypes.add(birdType);
    }

    public boolean isSubtype(final BirdType birdType) {
        assertNotNull("birdType", birdType);
        if (subTypes.contains(birdType)) {
            return true;
        }
        return subTypes.parallelStream().anyMatch(subType -> subType.isSubtype(birdType));
    }

    public boolean hasTypeInstanceOf(Bird instance) {
        if (instance == null) {
            return false;
        }
        if (instance.getBirdType().equals(this)) {
            return true;
        }
        return subTypes.parallelStream().anyMatch(subType -> subType.hasTypeInstanceOf(instance));
    }

    public BirdType getSuperType() {
        return superType;
    }

    public void setSuperType(BirdType superType) {
        this.superType = superType;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BirdType birdType = (BirdType) o;
        return Objects.equals(name, birdType.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    private static void assertNotNull(final String name, final Object value) {
        if (value == null) {
            throw new IllegalArgumentException("BirdType variable " + name + " is null");
        }
    }
}
