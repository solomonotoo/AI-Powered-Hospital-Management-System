import React, { useState } from "react";
import {
  StaffFormInput,
  StaffFormValues,
  staffSchema,
} from "../../schema/staff-schema";
import { BasicInfoStep } from "./steps/basic-info-step";
import { wizardSteps } from "./wizard-steps";
import { WizardProgressBar } from "./wizard-progress-bar";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { staffFormDefaultValues } from "../../schema/staff-form-default-values";
import { WizardNavigation } from "./wizard-navigation";

interface CreateStaffFormProps {
  onSubmit: (values: StaffFormValues) => void | Promise<void>;
  defaultValues?: Partial<StaffFormInput>;
}

export function CreateStaffForm({
  onSubmit,
  defaultValues = {},
}: CreateStaffFormProps) {
  const [stepIndex, setStepIndex] = useState(0);

  const step = wizardSteps[stepIndex];
  const StepPanel = step.component;

  const isLast = stepIndex === wizardSteps.length - 1;

  //NB without useForm<StaffFormInput, unknown, StaffFormValues>
  const form = useForm<StaffFormInput, unknown, StaffFormValues>({
    resolver: zodResolver(staffSchema),
    // ...staffFormDefaultValues, ...defaultValues  allows the component to support both:
    // creating a new staff member
    // editing an existing staff member
    defaultValues: {
      ...staffFormDefaultValues,
      ...defaultValues,
    },
    mode: "onTouched",
  });

  async function next() {
    if (isLast) {
      return;
    }

    if (step.schema) {
      const fields = Object.keys(step.schema.shape) as (keyof StaffFormInput)[];

      console.log("Validating:", step.id);
      console.log("Fields:", fields);

      const isValid = await form.trigger(fields, {
        shouldFocus: true,
      });

      if (!isValid) {
        return;
      }
    }

    setStepIndex((index) => index + 1);
  }

  async function submit(values: StaffFormValues) {
    await onSubmit(values);
  }

  return (
    <div>
      <WizardProgressBar steps={wizardSteps} activeIndex={stepIndex} />

      <div>
        <form onSubmit={form.handleSubmit(submit)}>
          <StepPanel form={form} />

          <WizardNavigation
            isFirst={stepIndex === 0}
            isLast={isLast}
            loading={form.formState.isSubmitting}
            onBack={() => setStepIndex((i) => i - 1)}
            onNext={next}
          />
        </form>
      </div>
    </div>
  );
}

// ---------------------------------------------------------------------------
// wizard steps
// ---------------------------------------------------------------------------

import { cn } from "@/lib/utils";
import { wizardSteps } from "./wizard-steps";
import { Check } from "lucide-react";

// ---------------------------------------------------------------------------
// Progress bar
// ---------------------------------------------------------------------------
interface WizardProgressBarProps {
  steps: typeof wizardSteps;
  activeIndex: number;
}

export function WizardProgressBar({
  steps,
  activeIndex,
}: WizardProgressBarProps) {
  return (
    <ol className="flex w-full items-center">
      {steps.map((step, index) => {
        const done = index < activeIndex;
        const active = index === activeIndex;
        return (
          <li key={step.id} className="flex flex-1 items-center last:flex-none">
            <div className="flex flex-col items-center gap-1">
              <div
                className={cn(
                  "flex h-7 w-7 items-center justify-center rounded-full border text-xs font-medium transition-colors",
                  done && "border-primary bg-primary text-primary-foreground",
                  active && "border-primary text-primary",
                  !done &&
                    !active &&
                    "border-muted-foreground/30 text-muted-foreground"
                )}
              >
                {done ? <Check className="h-3.5 w-3.5" /> : index + 1}
              </div>
              <span
                className={cn(
                  "hidden text-[11px] sm:block",
                  active ? "font-medium" : "text-muted-foreground"
                )}
              >
                {step.title}
              </span>
            </div>
            {index < wizardSteps.length - 1 && (
              <div
                className={cn(
                  "mx-2 h-px flex-1 transition-colors",
                  done ? "bg-primary" : "bg-muted-foreground/30"
                )}
              />
            )}
          </li>
        );
      })}
    </ol>
  );
}

import { Button } from "@/components/ui/button";

interface WizardNavigationProps {
  isFirst: boolean;
  isLast: boolean;
  loading: boolean;
  onBack: () => void;
  onNext: () => void;
}

export function WizardNavigation({
  isFirst,
  isLast,
  loading,
  onBack,
  onNext,
}: WizardNavigationProps) {
  return (
    <div className="flex justify-between pt-6">
      <Button
        type="button"
        variant="outline"
        onClick={onBack}
        disabled={isFirst || loading}
      >
        Back
      </Button>

      <Button
        type={isLast ? "submit" : "button"}
        onClick={isLast ? undefined : onNext}
        disabled={loading}
      >
        {isLast ? (loading ? "Saving..." : "Save") : "Next"}
      </Button>
    </div>
  );
}



import { StaffFormStepProps } from '@/features/staff/types/staff';
import { useWatch } from 'react-hook-form';

export  function ReviewStep({ form }: StaffFormStepProps) {
    const { control } = form;
  const v = useWatch({ control });
  const summary = [
    ["Staff", [v.firstName, v.lastName].filter(Boolean).join(" ")],
    ["Employee number", v.employmentNumber],
    ["License number", v.licenseNumber],
    ["work email", v.workEmail],
    ["phone", v.phoneNumber],
    ["Department", v.department],
    ["Employment date", v.employmentDate],
  ].filter(([, val]) => val) as [string, string][];

  return (
    <div className="p-4 text-sm">
    <p className="mb-3 font-medium">Review</p>
    <div className="space-y-1.5 text-muted-foreground">
      {summary.map(([key, val]) => (
        <div key={key} className="flex justify-between gap-4">
          <span>{key}</span>
          <span className="font-medium text-foreground">{val}</span>
        </div>
      ))}
    </div>
  </div>
  )
}

import { FieldGroup } from "@/components/ui/field";
import { AppFormField } from "@/features/forms/fields/app-form-field";
import { StaffFormStepProps } from "@/features/staff/types/staff";

export const ProfessionalInfoStep = ({ form }: StaffFormStepProps) => {
  const { control } = form;

  return (
    <FieldGroup>
      <div className="grid grid-cols-1 gap-5 sm:grid-cols-2">

        <AppFormField
          control={control}
          name="specialisation"
          label="Specialisation"
          description="specialise field for doctors"
        />
        <AppFormField
          control={control}
          name="licenseNumber"
          label="License number"
          description="license number for doctors and nurses"
        />
        <AppFormField
          control={control}
          name="qualifications"
          label="qualifications"
          description="education qualification"
        />
        <AppFormField
          control={control}
          name="consultationFee"
          label="Consultation Fee"
          description="Fees charged by doctors"
        />
      </div>
    </FieldGroup>
  );
};

import { Button } from "@/components/ui/button";

interface WizardNavigationProps {
  isFirst: boolean;
  isLast: boolean;
  loading: boolean;
  onBack: () => void;
  onNext: () => void;
}

export function WizardNavigation({
  isFirst,
  isLast,
  loading,
  onBack,
  onNext,
}: WizardNavigationProps) {
  return (
    <div className="flex justify-between pt-6">
      <Button
        type="button"
        variant="outline"
        onClick={onBack}
        disabled={isFirst || loading}
      >
        Back
      </Button>

      <Button
        type={isLast ? "submit" : "button"}
        onClick={isLast ? undefined : onNext}
        disabled={loading}
      >
        {isLast ? (loading ? "Saving..." : "Save") : "Next"}
      </Button>
    </div>
  );
}

// ---------------------------------------------------------------------------
// wizard steps
// ---------------------------------------------------------------------------

import { cn } from "@/lib/utils";
import { wizardSteps } from "./wizard-steps";
import { Check } from "lucide-react";

// ---------------------------------------------------------------------------
// Progress bar
// ---------------------------------------------------------------------------
interface WizardProgressBarProps {
  steps: typeof wizardSteps;
  activeIndex: number;
}

export function WizardProgressBar({
  steps,
  activeIndex,
}: WizardProgressBarProps) {
  return (
    <ol className="flex w-full items-center">
      {steps.map((step, index) => {
        const done = index < activeIndex;
        const active = index === activeIndex;
        return (
          <li key={step.id} className="flex flex-1 items-center last:flex-none">
            <div className="flex flex-col items-center gap-1">
              <div
                className={cn(
                  "flex h-7 w-7 items-center justify-center rounded-full border text-xs font-medium transition-colors",
                  done && "border-primary bg-primary text-primary-foreground",
                  active && "border-primary text-primary",
                  !done &&
                    !active &&
                    "border-muted-foreground/30 text-muted-foreground"
                )}
              >
                {done ? <Check className="h-3.5 w-3.5" /> : index + 1}
              </div>
              <span
                className={cn(
                  "hidden text-[11px] sm:block",
                  active ? "font-medium" : "text-muted-foreground"
                )}
              >
                {step.title}
              </span>
            </div>
            {index < wizardSteps.length - 1 && (
              <div
                className={cn(
                  "mx-2 h-px flex-1 transition-colors",
                  done ? "bg-primary" : "bg-muted-foreground/30"
                )}
              />
            )}
          </li>
        );
      })}
    </ol>
  );
}

import { staffStepDefinitions } from "../../schema/staff-step-definitions";
import { BasicInfoStep } from "./steps/basic-info-step";
import { EmploymentAndAssignmentStep } from "./steps/employment-and-assignment-step";
import { ProfessionalInfoStep } from "./steps/professional-info-step";
import { ReviewStep } from "./steps/review-step";

export const wizardSteps = [
  { ...staffStepDefinitions.basicInfo, component: BasicInfoStep },
  {
    ...staffStepDefinitions.employment,
    component: EmploymentAndAssignmentStep,
  },
  { ...staffStepDefinitions.professional, component: ProfessionalInfoStep },
  { ...staffStepDefinitions.review, component: ReviewStep },
];

// ---------------------------------------------------------------------------
// Step registry — drives the wizard's progress bar, validation-per-step,
// and the field list each step is responsible for.
// ---------------------------------------------------------------------------

import {
  basicInformationSchema,
  employmentAndAssignmentSchema,
  professionalInformationSchema,
} from "./staff-schema";

export const staffStepDefinitions = {
  basicInfo: {
    id: "personal",
    title: "Personal Info",
    description: "Identity",
    schema: basicInformationSchema,
  },
  employment: {
    id: "employment",
    title: "Employment Info",
    description: "Employment Basic Info ",
    schema: employmentAndAssignmentSchema,
  },
  professional: {
    id: "profession",
    title: "Professional Info",
    description: "Profession info",
    schema: professionalInformationSchema,
  },
  review: {
    id: "review",
    title: "Review Info",
    description: "Review your information",
    schema: undefined,
  },
};






