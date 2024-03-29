package com.nnnd.smartnote.ui.settings

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nnnd.smartnote.R

class PrivacyPolicy : AppCompatActivity() {

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)

        val recyclerView = findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        val users = ArrayList<User>()
        users.add(User("\n" + "Privacy Policy", "Last updated: December 8, 2021\n" +
                "\n" +
                "User privacy is the top priority. We (“3&D”) value your (“User”, “You” or “Your”) privacy and recognize the sensitivity of your personal information. We are committed " +
                "to protecting your personal information and using it only when appropriate to provide you with the best possible services and products.\n\n" +
                "\n" +
                "BY ACCESSING OR USING OUR SERVICES, YOU AGREE TO THE COLLECTION AND USE OF INFORMATION IN ACCORDANCE WITH THIS POLICY." +
                " THIS PRIVACY POLICY IS PART OF AND INCORPORATED INTO OUR TERMS OF USE(\"Terms of Use\"). IF YOU DO NOT AGREE TO ALL OF THE TERMS " +
                "AND CONDITIONS SET FORTH BELOW, YOU MAY NOT USE OUR SERVICES\n" +
                "\n" +
                "Should you have any questions related to this policy or our practices around privacy and data protection in general, please don’t " +
                "hesitate to contact us as indicated in the contact section of this policy.\n"))
        users.add(User("\n" + "Definitions\n", "Personal Information means information about a living individual who can be identified " +
                "from the information, including your name, address, email, telephone number, fax number, geographic location, etc.\n" +
                "Non-Personal Information means information that cannot be used to identify a specific individual. For example, the duration of a " +
                "page visit, the time when you use a service.\n"
                +
                "\n"))
        users.add(User("\n" + "What Information We Collect and How It Is Used?\n", "Generally, we do not collect " +
                "any Personal Information when you use our applications or websites. We do not require users to register before using " +
                "our service, nor do we require users to provide us with any Personal Information.\n" +
                "\n" +
                "The only possible way for us to access your Personal Information is if you voluntarily" +
                " send us your feedback, suggestions, or report issues via email. We may have the email address " +
                "and other personal information you voluntarily send to us. In this case, Personal Information will be " +
                "strictly protected and will only be used to contact you or improve our service. We will not disclose or" +
                " share your Personal Information with third parties. Please note that it is up to you to decide whether " +
                "to send us an email.\n" +
                "When you use our websites and applications, some Non-Personal Information will be collected automatically either generated" +
                " by the use of service or from the service infrastructure itself. For example, browser type, browser version, the pages of our" +
                " Service that you visit, the time and date of your visit, the time spent on those pages, clicks, device identifiers, and other diagnostic data. This information is " +
                "collected to improve the service we provide to you. The type and amount of information collected will depend on how you use our services. " +
                "We aggregate this information to help us provide customers with more useful information, and to better understand which parts of our website" +
                "and applications are most interesting to users, and how users use our services to improve our services . For the purposes of this" +
                " privacy policy, aggregated data is treated as Non-Personal Information. The Non-Personal Information may include, but is not limited to:\n"))
        users.add(User("\n" + "Log Data\n", " Log Data is used to compile various (anonymized) metrics to get a better understanding of how the websites " +
                "and applications are being used (which features are used most, how many times users are using ”search”, for example), " +
                "so that we can improve the user experience of our services and also make better decisions for future initiatives " +
                "like new features or services (better explanation of the product's features, for example).\n"))
        users.add(User("\n" + "Device and Internet Usage\n", " If you visit our websites or use our applications, we may collect information " +
                "from your device, including identifiers to help us identify your device’s hardware and operating system. Device information may " +
                "be accessed using industry-standard identifiers such as those approved " +
                "by your device operating system manufacturer. We use this information to better adapt our services to your device and operating system.\n\n" +
                "\n"))
        users.add(User("\n" + "Cookies\n", "Cookies are small data files that are stored on your device by a service. " +
                "Among other things, the use of cookies helps us to improve the service and your experience of the service. " +
                "We use cookies to see which areas and features of the service are most popular, to count the number of devices " +
                "accessing the site, to personalize your experience, and to remember your preferences. If your browser or device is set" +
                "not to accept cookies or if you reject a cookie, you may not be able to access certain features or services." +
                "\n"))
        users.add(User("\n" + "How Long We Retain Your Information?\n", " We do not collect or store your Personal Information. If you voluntarily" +
                " send us an email, we will keep your email address" +
                " and related information for a necessary and relevant time, so that we can contact you to give you feedback or improve our services.\n" +
                "We will retain some of the Non-Personal Information for internal analysis purposes. Non-Personal Information is generally retained for a shorter period, " +
                "except when this data is used to strengthen the security or to improve the functionality of our services, or we are legally obligated to retain this data for longer time periods.\n" +
                "\n"))
        users.add(User("\n" + "Security\n", "We value your trust in providing us your information, thus we are striving to use " +
                "commercially acceptable means of protecting it. But remember that no method of transmission over the internet," +
                " or method of electronic storage is 100% secure and reliable, and we cannot guarantee its absolute security.\n"))
        users.add(User("\n" + "Your Rights\n", "Decide whether to use our service: You can decide whether to download our applications. " +
                "When we update our policies," +
                " you can decide whether to continue using our services or not. You can opt-out of certain functions in our application settings." +
                "Decide on the permissions granted to the service: You can decide whether to grant certain permissions to our services in the system settings. " +
                "Please note that some functions cannot be run without specific permissions.If you are a resident of the European Union (EU) and the European Economic Area (EEA), you have " +
                "certain data protection rights described in the General Data Protection Regulation (GDPR).\n" +
                "If you are a California resident, you can implement your rights as provided in the California Consumer Privacy Act (CCPA)\n" +
                "If you have any requirements or questions, please contact us at: natlight.todo@gmail.com.\n"))
        users.add(User("\n" + "Children’s Privacy\n","Our Services are not intended for use by children under the age of 13 (“Children”). We are committed to protecting " +
                "the privacy needs of children and we encourage parents and guardians to take an active role in their children’s online activities and interests. We do not knowingly collect information " +
                "from children under the age of 13, and in the event that we learn or receive " +
                "a valid notice that a child under the age of 13 has provided information on the service, we will delete that information as soon as possible.\n" ))
        users.add(User("\n" + "Changes to This Privacy Policy\n", " \n" +
                "We may update our Privacy Policy from time to time. Thus, you are advised to review this page periodically for any changes. " +
                "We will notify you of any changes by posting the new Privacy Policy on this page.\n"))
        users.add(User("\n" + "Contact Us\n", "If you have any questions or suggestions about our Privacy Policy, please do not hesitate to contact " +
                "us at:natlight.todo@gmail.com\n"))
        val adapter = PrivPolicyAdapter(users)
        recyclerView.adapter = adapter
    }
}