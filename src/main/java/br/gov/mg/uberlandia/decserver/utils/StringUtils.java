package br.gov.mg.uberlandia.decserver.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringUtils {

    private static final Logger logger = LoggerFactory.getLogger(StringUtils.class.getName());

    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    private static final String ONLY_DIGITS_REGEX = "\\d+";
    private static final Pattern ONLY_DIGITS_PATTERN = Pattern.compile(ONLY_DIGITS_REGEX);

    private static final String NON_DIGIT_REGEX = "[^\\d]";
    private static final Pattern NON_DIGIT_PATTERN = Pattern.compile(NON_DIGIT_REGEX);

    private static final int[] CPF_WEIGHTS = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] CNPJ_WEIGHTS = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};


    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean equals(String str1, String str2) {
        return isEmpty(str1) ? isEmpty(str2) : str1.equals(str2);
    }

    public static boolean isCpf(String cpf) {
        if ((cpf == null) || (cpf.length() != 11) || !containsOnlyDigits(cpf) || allCharactersAreSame(cpf))
            return false;
        Integer digit1 = calculateDigit(cpf.substring(0, 9), CPF_WEIGHTS);
        Integer digit2 = calculateDigit(cpf.substring(0, 9) + digit1, CPF_WEIGHTS);
        return cpf.equals(cpf.substring(0, 9) + digit1.toString() + digit2.toString());
    }

    public static boolean isCnpj(String cnpj) {
        if ((cnpj == null) || (cnpj.length() != 14) || !containsOnlyDigits(cnpj) || allCharactersAreSame(cnpj))
            return false;
        Integer digit1 = calculateDigit(cnpj.substring(0, 12), CNPJ_WEIGHTS);
        Integer digit2 = calculateDigit(cnpj.substring(0, 12) + digit1, CNPJ_WEIGHTS);
        return cnpj.equals(cnpj.substring(0, 12) + digit1.toString() + digit2.toString());
    }

    static boolean containsOnlyDigits(String str) {
        return str != null && ONLY_DIGITS_PATTERN.matcher(str).matches();
    }

    static boolean allCharactersAreSame(String str) {
        return str != null && str.length() > 0 && str.chars().allMatch(e -> e == str.charAt(0));
    }

    private static int calculateDigit(String str, int[] weights) {
        int sum = 0;
        for (int i = str.length() - 1, digit; i >= 0; i--) {
            digit = Integer.parseInt(str.substring(i, i + 1));
            sum += digit * weights[weights.length - str.length() + i];
        }
        sum = 11 - sum % 11;
        return sum > 9 ? 0 : sum;
    }

    public static String removeNonDigits(String str) {
        return str != null ? NON_DIGIT_PATTERN.matcher(str).replaceAll("") : null;
    }

    /**
     * Verifica se uma dada String é um email válido.
     */
    public static boolean isEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static String formatCPF(final String cpf) {
        try {
            String _cpf = removeNonDigits(cpf);
            if (!isCpf(_cpf)) throw new IllegalArgumentException("CPF inválido.");
            String formattedCpf = _cpf.substring(0, 3) + ".";
            formattedCpf += _cpf.substring(3, 6) + ".";
            formattedCpf += _cpf.substring(6, 9) + "-";
            formattedCpf += _cpf.substring(9, 11);
            return formattedCpf;
        } catch (Exception e) {
            logger.warn("Não foi possível formatar o cpf: " + cpf + ".", e);
            return cpf;
        }
    }

    public static String formatCNPJ(final String cnpj) {
        try {
            String _cnpj = removeNonDigits(cnpj);
            if (!isCnpj(_cnpj)) throw new IllegalArgumentException("CNPJ inválido.");
            String formattedCnpj = _cnpj.substring(0, 2) + ".";
            formattedCnpj += _cnpj.substring(2, 5) + ".";
            formattedCnpj += _cnpj.substring(5, 8) + "/";
            formattedCnpj += _cnpj.substring(8, 12) + "-";
            formattedCnpj += _cnpj.substring(12, 14);
            return formattedCnpj;
        } catch (Exception e) {
            logger.warn("Não foi possível formatar o cnpj: " + cnpj + ".", e);
            return cnpj;
        }
    }

    public static String formatCEP(final String cep) {
        if (isEmpty(cep)) return null;
        try {
            String _cep = removeNonDigits(cep);
            if (!containsOnlyDigits(_cep) || _cep.length() != 8) throw new IllegalArgumentException("CEP inválido.");
            StringBuilder formattedCep = new StringBuilder(9);
            formattedCep.append(_cep.substring(0, 5));
            formattedCep.append('-');
            formattedCep.append(_cep.substring(5, 8));
            return formattedCep.toString();
        } catch (Exception e) {
            logger.warn("Não foi possível formatar o cep: " + cep + ".", e);
            return cep;
        }
    }

    public static String removePrefix(String string, final String prefix) {
        return string != null && prefix != null && !prefix.isEmpty() && string.startsWith(prefix)
            ? string.substring(string.lastIndexOf(prefix) + prefix.length())
            : string;
    }

    /**
     * Converte o orignalNumber para String adicionando zeros a esquerda sem ultrapassar o tamanho maxLength.
     * A String final vai ter no maximo maxLength digitos.
     *
     * @param originalNumber
     * @param maxLength
     * @return
     */
    public static String prefixLeadingZeroes(int originalNumber, int maxLength) {
        return String.format("%0" + maxLength + "d", originalNumber);
    }

    /**
     * Substitui um caractere acentuado pelo mesmo caractere sem acentuacao.
     *
     * @param str
     * @return
     */
    public static String substituirAcentos(String str) {
        return str == null ? null : Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    /**
     * Remove os caracteres especiais de uma string.
     * Essa funcao tambem remove os acentos (substitui por vazio).
     *
     * @param str
     * @return
     */
    public static String removerCaracteresEspeciaisMenosEspaco(String str) {
        return str == null ? null : str.replaceAll("[^a-zA-Z0-9\\s]", "");
    }

    /**
     * Remove os espacos iniciais e finais de uma string (trim).
     * Remove os espacos duplicados da string e substitui por uma string qualquer.
     *
     * @param str
     * @param substituirPor
     * @return
     */
    public static String removerEspacos(String str, String substituirPor) {
        // Remove os espacos duplicados, depois troca o espaco por outro caractere
        return str == null ? null : str.replaceAll(" +", " ").trim().replaceAll("[\\s+]", substituirPor);
    }

}
