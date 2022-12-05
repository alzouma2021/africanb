package com.africanb.africanb.utils.emailService;

/**
 * @author Alzouma Moussa Mahamadou
 */
public class BodiesOfEmail {

    public  String bodyHtmlMailCreateCompagny() {
        String bodyHtmlMailCreateCompagny = "<p style=\\\"color: #2e07f7; text-align: justify;\\\">\"\n" +
                "                + \"<span style=\\\"font-family: courier new, courier; font-size: medium;\\\">\"\n" +
                "                + \"Bonjour Madame/Monsieur,</span></p>\"\n" +
                "                + \"<p style=\\\"color: #2e07f7; text-align: justify;\\\"><span style=\\\"font-family: courier new, courier; font-size: medium;\\\">\\n\"\n" +
                "                + \"Par ce present,nous voudrions vous informer de la cr&eacute;ation effective de votre soci&eacute;t&eacute; de transport sur notre plateforme <strong>AFRICANB.</strong></span></p>\\n\"\n" +
                "                + \"<p style=\\\"color: #2e07f7; text-align: justify;\\\"><span style=\\\"font-family: courier new, courier; font-size: medium;\\\">Nous allons proceder aux differentes verifications necessaires pour la validation de votre requ&ecirc;te.</span></p>\\n\"\n" +
                "                + \"<p style=\\\"color: #2e07f7; text-align: justify;\\\"><span style=\\\"font-family: courier new, courier; font-size: medium;\\\">Vous recevrez ulterieurement une reponse.</span></p>\\n\"\n" +
                "                + \"<p style=\\\"color: #2e07f7; text-align: justify;\\\"><strong><span style=\\\"font-family: courier new, courier; font-size: medium;\\\">Merci pour la confiance plac&eacute;e en nous.</span></strong></p>\\n\"\n" +
                "                + \"<p style=\\\"color: #2e07f7; text-align: justify;\\\"><span style=\\\"font-family: courier new, courier; font-size: medium;\\\">Cordialement,</span></p>" ;
        return "Bonjour Madame/Monsieur,\n\n" +
                "Par ce présent, nous venons vous informer de la création effective de votre compagnie de transport sur notre plateforme Africanb.\n\n" +
                "Nous allons procéder à la vérification de votre demande d'adhésion à notre compagnie.\n\n" +
                "Par conséquent, vous recevrez ultérieurement une reponse de notre part.\n\n" +
                "Cordialement,";
    }

    public  String bodyHtmlMailValidateCompagny() {
        String bodyHtmlMailValidateCompagny = " \"<p style=\\\"color: #2e07f7; text-align: justify;\\\">\"\n" +
                "                + \"<span style=\\\"font-family: courier new, courier; font-size: medium;\\\">\\\"\n" +
                "                + \"Bonjour Madame/Monsieur,</span></p>\"\n" +
                "                + \"<p style=\\\"color: #2e07f7; text-align: justify;\\\"><span style=\\\"font-family: courier new, courier; font-size: medium;\\\">\"\n" +
                "                + \"Par ce present,nous avons le plaisir de vous informer que votre demande d'adh&eacute;sion a &eacute;t&eacute; valid&eacute;e.Par cons&eacute;quent,nous vous invitons &agrave; vous connecter sur compte pour finaliser dans le bref d&eacute;lai votre inscription.</span></p>\"\n" +
                "                + \"<p style=\\\"color: #2e07f7; text-align: justify;\\\"><span style=\\\"font-family: courier new, courier; font-size: medium;\\\">Cordialement,</span></p>\"";
        return bodyHtmlMailValidateCompagny;
    }

}
