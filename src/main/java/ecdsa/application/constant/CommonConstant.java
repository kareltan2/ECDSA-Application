package ecdsa.application.constant;

/**
 * @author kareltan
 */
public class CommonConstant {

  private CommonConstant(){
  }

  public static final String COLON = ":";

  public static final String WHITE_SPACE = " ";

  public static final String FULLSTOPS = ".";

  public static final String SLASH = "/";

  public static final String CURVE = "secp256r1";

  public static final String DEFAULT_TITLE_TAB_PAGE = "ECDSA Application";

  public static final String EC = "EC";

  public static final String BC = "BC";

  public static final String SHA256_ECDSA = "SHA256withECDSA";

  public static final String START = "Start";

  public static final String RESULT = "Result";

  public static final String BACK_TO_PREVIOUS_PAGE = "Back to Previous Page";

  public static final String GENERATE = "Generate Key Pair";

  public static final String SAVE_TO_FILE = "Save To File";

  public static final String KEY_GENERATION = "Key Generation";

  public static final String VERIFICATION = "Verification";

  public static final String SIGNING = "Signing";

  public static final String ABOUT = "About";

  public static final String QNA = "QnA";

  public static final String AUTHENTICATE = "Authenticate";

  public static final String FILE_NAME = "File Name";

  public static final String PRIVATE_KEY = "Private Key";

  public static final String LABEL_PRIVATE_KEY = PRIVATE_KEY + WHITE_SPACE + FILE_NAME;

  public static final String PUBLIC_KEY = "Public Key";

  public static final String LABEL_PUBLIC_KEY = PUBLIC_KEY + WHITE_SPACE + FILE_NAME;

  public static final String FILE = "File";

  public static final String ORIGINAL_FILE = "Original File";

  public static final String CHOSEN = "Chosen";

  public static final String FILE_CHOSEN_LABEL = FILE + WHITE_SPACE + CHOSEN;

  public static final String FOLDER = "Target Folder";

  public static final String SIGNED = "Signed";

  public static final String SIGNED_FILE = SIGNED + WHITE_SPACE + FILE_NAME;

  public static final String SIGNED_FILE_NAME_LABEL = SIGNED + WHITE_SPACE + FILE;

  public static final String DEFAULT_FONT = "Arial";

  public static final String APPLICATION_SLOGAN = "Empower Your Documents with Precision and Trust";

  public static final String APPLICATION_DESCRIPTION = "Embark on a transformative journey into the future of document security with our<br>" +
          " revolutionary application! Introducing a groundbreaking experience in digital<br> signatures – witness the " +
          "seamless fusion of advanced<br> cryptography and cutting-edge technology.";

  public static final int DEFAULT_WIDTH = 800;

  public static final int DEFAULT_HEIGHT = 600;

  public static final String LANDING_PAGE_GUI = "Landing Page GUI";

  public static final String DOCUMENTATION = "Documentation";

  public static final String PAGE = "Page";

  public static final String KEY_GENERATION_PAGE = KEY_GENERATION + WHITE_SPACE + PAGE;

  public static final String SIGNING_PAGE = SIGNING + WHITE_SPACE + PAGE;

  public static final String VERIFICATION_PAGE = VERIFICATION + WHITE_SPACE + PAGE;

  public static final String KEY_GENERATION_RESULT_PAGE = KEY_GENERATION + WHITE_SPACE + RESULT + WHITE_SPACE + PAGE;

  public static final String SIGNING_RESULT_PAGE = SIGNING + WHITE_SPACE + RESULT + WHITE_SPACE + PAGE;

  public static final String LOADING = "Loading";

  public static final String PLEASE_WAIT = "Please Wait...";

  public static final String MESSAGE_NOTES = "Message Notes";

  public static final String MESSAGE_NOTES_LABEL = MESSAGE_NOTES + COLON;

  public static final String MESSAGE_CONTENT = "Please be careful when inputting file names";

  public static final String MESSAGE_DIALOG_CONFIRMATION_BACK = "Are you sure you want to back? All the data will be remove";

  public static final String MESSAGE_DIALOG_CONFIRMATION_CLEAR = "Are you sure you want to clear all the data?";

  public static final String CONFIRMATION_DIALOG_TITLE = "Confirmation";

  public static final String SUCCESS_DIALOG_TITLE = "Successfully Generated!";

  public static final String MESSAGE_DIALOG_CONFIRMATION_SUCCESS_GENERATED = SUCCESS_DIALOG_TITLE + WHITE_SPACE +  "Do you want to go to next step?";

  public static final String WARNING_EMPTY_FIELD_DIALOG_TITLE = "Validation Error";

  public static final String WARNING_EMPTY_FIELD_DIALOG_MESSAGE = "Please fill in all required fields.";

  public static final String WARNING_EXTENSION_FILE_DIALOG_TITLE = "Validation Document Type Error";

  public static final String WARNING_EXTENSION_FILE_DIALOG_MESSAGE = "Unsupported Type of Document";

  public static final String WARNING_KEY_NOT_VALID_DIALOG_TITLE = "Key is not valid!";

  public static final String WARNING_KEY_NOT_VALID_DIALOG_MESSAGE = "Inputted key is not valid, please check the inputted key file!";

  public static final String WARNING_SIGNATURE_NOT_VALID_DIALOG_TITLE = "Signature is not valid!";

  public static final String WARNING_SIGNATURE_NOT_VALID_DIALOG_MESSAGE = "Inputted signed file has an invalid signature barcode, please check the inputted signed file!";

  public static final String ERROR_DIALOG_TITLE = "Error Occurred";

  public static final String ERROR_DIALOG_MESSAGE = "There is error occurred, please try again!";

  public static final String CLEAR_INPUT = "Clear Input";

  public static final String BROWSE = "Browse";

  public static final String PDF = "pdf";

  public static final String DOCUMENTS = "Documents";

  public static final String SIGNATURE = "Signature";

  public static final String VERIFICATION_DIALOG_TITLE = "Result Verification";

  public static final String VERIFICATION_DIALOG_MESSAGE_VALID = "Result Verification: Signature valid!";

  public static final String VERIFICATION_DIALOG_MESSAGE_NOT_VALID = "Result Verification: Signature not valid!";

  public static final String PRIVATE_KEY_EXTENSION = "pvk";

  public static final String PUBLIC_KEY_EXTENSION = "pbk";

  public static final String SIGNATURE_EXTENSION = "sig";

  public static final String ABOUT_DESCRIPTION = "Welcome to Digital Signature Application, " +
          "the forefront of cutting-edge technology in the realm of digital signatures. " +
          "By this application, we take pride in revolutionizing secure document transactions through our state-of-the-art " +
          "Digital Signature Application. Our commitment to excellence is evident in our implementation of the Elliptic Curve Algorithm, " +
          "utilizing a robust 256-bit hash function for unparalleled security and efficiency.<br><br>" +
          "<b>Who We Are:</b> <br>" +
          "we are driven by a passion for innovation and a dedication to providing seamless " +
          "solutions for secure digital interactions. Our team comprises experts in cryptography, " +
          "software development, and information security, united by a common goal: " +
          "to empower individuals and organizations with a reliable and secure digital signature platform.<br><br>" +
          "<b>The Signing and Verification Process</b> <br>" +
          "1. Key Generation <br>" +
          "Our Digital Signature Application initiates the process by generating a unique pair " +
          "of cryptographic keys—public and private—based on the Elliptic Curve Algorithm. " +
          "This ensures a secure foundation for the entire signing and verification journey.<br><br>" +
          "2. Hashing the Document <br>" +
          "Before signing, the document undergoes a rigorous hashing process using a 256-bit hash function. " +
          "This produces a fixed-size hash value, a digital fingerprint of the document that is unique " +
          "and irreversible.<br><br>" +
          "3. Signing the Document <br>" +
          "The private key is then used to create the digital signature, which is mathematically " +
          "linked to the hashed document. This signature serves as a tamper-evident seal, " +
          "providing assurance that the document has not been altered since it was signed.<br><br>" +
          "4. Verification Process <br>" +
          "To verify a signature, the recipient uses the public key to decrypt the digital signature, " +
          "revealing the hash value. The document is then hashed again, and the two hash values are compared. " +
          "If they match, the signature is valid, confirming the document's integrity and authenticity.<br><br>" +
          "<b>The Digital Signature Advantage</b> <br>" +
          "1. Unrivaled Security <br>" +
          "Security lies at the core of our Digital Signature Application." +
          " We leverage the Elliptic Curve Algorithm, a cutting-edge cryptographic technique, " +
          "to ensure the highest level of protection for your digital signatures. " +
          "With a 256-bit hash function forming the backbone of our algorithm, " +
          "we provide an impregnable fortress against unauthorized access and data breaches.<br><br>" +
          "2. Seamless Integration <br>" +
          "Our Digital Signature Application seamlessly integrates into your existing " +
          "workflows and processes. Whether you are a small business, " +
          "a government entity, or a large corporation, our flexible " +
          "and scalable solution adapts to your unique needs. Enhance the " +
          "efficiency of your document signing and verification processes with " +
          "our user-friendly application.<br><br>" +
          "3. User-Friendly Interface <br>" +
          "We understand the importance of user experience. That's why we've designed " +
          "our application with an intuitive and user-friendly interface. Sign documents with ease, " +
          "and verify signatures effortlessly, all within a few clicks. Our goal is to make the digital " +
          "signature process straightforward and accessible to users of all levels of technical expertise";

  public static final String QNA_DESCRIPTION = "<b>Q1: What is a digital signature, and why is it important?</b> <br>" +
          "A1: A digital signature is a cryptographic technique used to verify the authenticity and integrity " +
          "of digital messages or documents. It provides a secure way " +
          "to ensure that the sender is who they claim to be and that the content " +
          "has not been tampered with. Digital signatures are crucial for " +
          "secure online transactions and document exchanges, offering a higher level of trust and security " +
          "than traditional signatures.<br><br>" +

          "<b>Q2: Can I use the Digital Signature Application for my business, regardless of its size?</b> <br>" +
          "A2: Absolutely! Our Digital Signature Application is designed to " +
          "be flexible and scalable, making it suitable for businesses of all sizes. " +
          "Whether you're a small startup or a large corporation, our solution can be customized to " +
          "meet your specific needs. Enhance the security and efficiency of your document transactions " +
          "with our user-friendly and adaptable application.<br><br>" +

          "<b>Q3: How does the verification process work, and why is it important?</b> <br>" +
          "A3: The verification process involves using the recipient's public key to" +
          " decrypt the digital signature, revealing the hash value. The document is " +
          "then hashed again, and the two hash values are compared. If they match, the signature is " +
          "valid, confirming the document's integrity and authenticity. Verification is crucial for ensuring that " +
          "the signed document has not been altered during transmission and that it originated " +
          "from the claimed sender.<br><br>" +

          "<b>Q4: How does the Elliptic Curve Algorithm enhance security in digital signatures?</b> <br>" +
          "A4: The Elliptic Curve Algorithm is a modern and efficient cryptographic " +
          "method used for digital signatures. It leverages the mathematical properties of " +
          "elliptic curves to provide strong security with shorter key lengths, " +
          "making it computationally more efficient. This ensures a high level of " +
          "protection against unauthorized access and ensures the confidentiality and " +
          "integrity of the signed documents.<br><br>" +

          "<b>Q5: Why is a 256-bit hash function significant in the signing and verification process?</b> <br>" +
          "A5: The 256-bit hash function plays a crucial role in the security of " +
          "our Digital Signature Application. It produces a fixed-size hash value that serves " +
          "as a unique digital fingerprint for each document. The use of a " +
          "256-bit hash function ensures a high level of collision resistance, " +
          "making it extremely difficult for two different documents to produce the same " +
          "hash value. This adds an extra layer of security to the signing and verification process.<br><br>";

  public static final String FORMAT_DATE_PRINT = "EEEE, dd MMMM yyyy";

  public static final String SIGNED_AUTO_GENERATED_TEXT = "Has been signed by author at ";

  public static final int BLACK_BIT = 0xFF000000;

  public static final int WHITE_BIT = 0xFFFFFFFF;

}
