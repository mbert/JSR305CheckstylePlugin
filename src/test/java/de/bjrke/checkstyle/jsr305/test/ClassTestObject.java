package de.bjrke.checkstyle.jsr305.test;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.ParametersAreNullableByDefault;

import edu.umd.cs.findbugs.annotations.ReturnValuesAreNonnullByDefault;

public class ClassTestObject {

    // error, double annotation
    @ParametersAreNonnullByDefault
    @ParametersAreNullableByDefault
    class BothParameterAnnotationsClass {

    }

    // no error
    @ParametersAreNullableByDefault
    interface ParameterAnnotationsInterface {

        // no error
        public void setUnannotated( String unannotated );

        // error, redundant
        public void setNullable( @Nullable String nullable );

        // no error
        public void setNonnull( @Nonnull String nonnull );

    }

    interface NoParameterAnnotationsInterface {

        // error, missing
        public void setUnannotated( String unannotated );

        // no error
        public void setNullable( @Nonnull String nullable );

        // no error
        public void setNonnull( @Nullable String nonnull );

    }

    @ParametersAreNonnullByDefault
    class ParameterAnnotationsClass {

        // no error
        public void setUnannotated( @SuppressWarnings("unused") final String unannotated ) {
        }

        // no error
        public void setNullable( @SuppressWarnings("unused") @Nullable final String nullable ) {
        }

        // error, redundant
        public void setNonnull( @SuppressWarnings("unused") @Nonnull final String nonnull ) {
        }

    }

    class NoParameterAnnotationsClass {

        // error, missing
        public void setUnannotated( @SuppressWarnings("unused") final String unannotated ) {
        }

        // no error
        public void setNullable( @SuppressWarnings("unused") @Nullable final String nullable ) {
        }

        // no error
        public void setNonnull( @SuppressWarnings("unused") @Nonnull final String nonnull ) {
        }

    }

    @ParametersAreNonnullByDefault
    static class DefaultNullableParameterInheritingClass implements ParameterAnnotationsInterface {

        @Override
        // no error
        public void setUnannotated( final String unannotated ) {
        }

        @Override
        // no error
        public void setNullable( final String nullable ) {
        }

        @Override
        // no error
        public void setNonnull( final String nonnull ) {
        }

        // no error
        public void setAnotherUnannotated( @SuppressWarnings("unused") final String unannotated ) {
        }
    }

    static class NullableParameterInheritingClass implements ParameterAnnotationsInterface {

        @Override
        // no error
        public void setUnannotated( final String unannotated ) {
        }

        @Override
        // no error
        public void setNullable( final String nullable ) {
        }

        @Override
        // no error
        public void setNonnull( final String nonnull ) {
        }

        // error
        public void setAnotherUnannotated( @SuppressWarnings("unused") final String unannotated ) {
        }
    }

    @ReturnValuesAreNonnullByDefault
    interface ReturnValueAnnotationInterface {
        // error, redundant
        @Nonnull
        public String getNonnull();

        // error, disallowed
        @ReturnValuesAreNonnullByDefault
        public String getNonnullByDefault();

        // no error
        @CheckForNull
        public String getCheckForNull();
    }

    @ReturnValuesAreNonnullByDefault
    static class ReturnValueAnnotationClass {
        // error, redundant
        @Nonnull
        public String getNonnull() {
            return "";
        }

        // error, disallowed
        @ReturnValuesAreNonnullByDefault
        public String getNonnullByDefault() {
            return "";
        }

        // no error
        @CheckForNull
        public String getCheckForNull() {
            return "";
        }
    }

    interface NoReturnValueAnnotationInterface {
        // no error
        @Nonnull
        public String getNonnull();

        // error, disallowed
        @ReturnValuesAreNonnullByDefault
        public String getNonnullByDefault();

        // no error
        @CheckForNull
        public String getCheckForNull();
    }

}

@ParametersAreNullableByDefault
class InheritanceTest {

    @ParametersAreNonnullByDefault
    interface Inherited {

        // no error
        void getUnannotated( String unannotated );

        // no error
        void getNullable( @Nullable String nullable );
    }

    // no error
    static class UnannotatedInheritor implements Inherited {

        // no error, since we're overriding @NonnullByDefault from the interface
        @Override
        public void getUnannotated( @Nullable final String unannotated ) {
        }

        // no error
        @Override
        public void getNullable( final String nullable ) {
        }

        // error
        public void getAnotherUnannotated( @SuppressWarnings("unused") final String unannotated ) {
        }

    }

    // no error
    @ParametersAreNullableByDefault
    static class AnnotatedInheritor implements Inherited {

        // this should not be an error, we are inheriting via the @Override
        // annotation
        @Override
        public void getUnannotated( final String unannotated ) {
        }

        // no error
        @Override
        public void getNullable( final String nullable ) {
        }

        // no error
        public void getAnotherUnannotated( @SuppressWarnings("unused") final String unannotated ) {
        }

    }

    // anonymous classes can't be annotated, but this should inherit the
    // annotations from the superclass
    public void testAnonClasses() {
        @SuppressWarnings("unused")
        final Inherited anonymousClassInstance = new Inherited() {

            // no error
            @Override
            public void getUnannotated( final String unannotated ) {
            }

            // no error
            @Override
            public void getNullable( final String nullable ) {
            }

            // error
            public void getAnotherUnannotated( final String getAnotherUnannotated ) {
            }
        };
    }

}

class EnumTest {

    @ParametersAreNonnullByDefault
    enum ParameterAnnotationsEnum {
        TEST;

        // no error
        public void setUnannotated( @SuppressWarnings("unused") final String foo ) {
        }

        // no error
        public void setNullable( @SuppressWarnings("unused") @Nullable final String foo ) {
        }

        // error, redundant
        public void setNonnull( @SuppressWarnings("unused") @Nonnull final String foo ) {
        }

    }

    enum NoParameterAnnotationsEnum {
        TEST;

        // error, missing
        public void setUnannotated( @SuppressWarnings("unused") final String foo ) {
        }

        // no error
        public void setNullable( @SuppressWarnings("unused") @Nullable final String foo ) {
        }

        // no error
        public void setNonnull( @SuppressWarnings("unused") @Nonnull final String foo ) {
        }

    }

    @ReturnValuesAreNonnullByDefault
    enum ReturnValueAnnotationEnum {
        TEST;
        // error, redundant
        @Nonnull
        public String getNonnull() {
            return "";
        }

        // error, disallowed
        @ReturnValuesAreNonnullByDefault
        public String getNonnnullByDefault() {
            return "";
        }

        // no error
        @CheckForNull
        public String getCheckForNull() {
            return "";
        }
    }

    // error
    @ParametersAreNonnullByDefault
    @ParametersAreNullableByDefault
    enum BothAnnotations {
        TEST;
    }

}
