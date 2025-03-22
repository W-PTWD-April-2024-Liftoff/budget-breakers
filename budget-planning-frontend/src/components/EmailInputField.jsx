import InputField from "./InputField";

const EmailInputField = ({ label, name, value, setFormData }) => {
    return <InputField label={label} name={name} value={value} setFormData={setFormData} type="email" />;
};

export default EmailInputField;