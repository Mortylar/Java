package edu.school21.preprocessor;

public class PreProcessorToLowerImpl implements PreProcessor {

    @Override
    public String process(String victim) {
        return victim.toLowerCase();
    }
}
